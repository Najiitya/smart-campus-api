# Smart Campus Sensor & Room Management API

## 1. API Overview
The Smart Campus API is a robust, lightweight RESTful web service built using Java and JAX-RS (Jersey). It is designed to manage university infrastructure by tracking Rooms and the various IoT Sensors (such as CO2 monitors and temperature gauges) deployed within them. The API utilizes an in-memory data structure for fast read/write operations and is deployed via an embedded Grizzly HTTP server. Key features include resource nesting (sub-resources for sensor readings), query parameter filtering, and a comprehensive suite of custom exception mappers to ensure a leak-proof, secure system architecture.

## 2. Build and Launch Instructions
To build and run this project locally, please follow these steps:

**Prerequisites:** * Java JDK 11 or higher installed.
* Apache Maven installed.
* An IDE such as Apache NetBeans.

**Steps to Launch:**
1. Clone the repository to your local machine.
2. Open the project folder (`CourseWork`) in your IDE.
3. Perform a clean build to resolve Maven dependencies and package the `jar` file:
   * In NetBeans: Click **Clean and Build** (or run `mvn clean package` in your terminal).
4. Navigate to `src/main/java/com/coursework/Main.java`.
5. Run the `Main.java` file. 
6. The embedded Grizzly server will start. Look for the console message: `SMART CAMPUS SERVER IS RUNNING!`
7. The base API will be accessible at: `http://localhost:8080/api/v1/`

## 3. Sample Interactions (cURL Commands)
Below are five sample commands demonstrating how to interact with the API:

**1. View API Discovery Metadata:**
```bash
curl -X GET http://localhost:8080/api/v1/


* Part 1: Service Architecture & Setup
*Question: In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions
*•	Answer: The way JAX-RS works by default is that it thinks of Resource classes as something that is made for each request. So when a new HTTP request comes in JAX-RS makes an instance of the class like RoomResource and gets rid of it as soon as it sends a response. This means we cannot save our data in the way like in a private Map called rooms because each time a new request comes in the data will be gone. Since we do not have a database we have to use static memory like a public static Map called rooms inside a class called DataRepository to keep our data safe and make sure it is still there when we need it again. This is how we can keep our data from being lost and make sure JAX-RS and RoomResource remember things, over requests.
*
*Question: Why is the provision of "Hypermedia" (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?
*•	Answer: HATEOAS, or Hypermedia as the Engine of Application State, allows APIs to independently determine actions by providing direct steps in JSON responses. This eliminates the need for developers to consult documentation for URLs. Client applications retrieve links from the Discovery endpoint, ensuring stability even if backend URLs change. This design keeps the client and server decoupled, enhancing flexibility and adaptability. By presenting URLs in responses, HATEOAS facilitates easier API updates and creates a more dynamic, manageable system for developers.
*
*Part 2: Room Management
*Question: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.
*•	Answer: When the server returns only IDs, data size is reduced, leading to lower network traffic and quicker initial API responses. However, the client must send additional requests to gather full room information. Conversely, when the server sends complete room details, it uses more bandwidth initially but enhances user experience by providing all necessary data in one trip, making the interface feel faster and improving user satisfaction.
*
*Question: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times
*•	Answer: In the approach, the DELETE action is indeed ineffective. When several identical requests have the same impact on the server’s state as a single request, this is known as the ineffectiveness. A 204 No content error is returned by the server when a client submits a DELETE request for Room A. The server simply returns a 404 Not Found because the room is no longer available if the client submits the exact same DELETE request twice or three times. After the first successful deletion, the system's general status doesn't alter.
*
*Part 3: Sensor Operations & Linking
*Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?
*•	Answer: The @Consumes annotation checks incoming requests to ensure they match expected formats. If a client sends data in an incorrect format like text/application/xml, JAX-RS intercepts and rejects the request, returning an HTTP 415 error to inform the client about the unsupported media type.
*Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?
*•	Answer: URL routes such as /sensors/123, are used in RESTful design to uniquely identify particular, hierarchical resources. Conversely, query parameters are intended to serve as sorting criteria, filters, or modifiers for a collection. Because filtering is more flexing and optional, using @QueryParam is better. While query parameters enable clients to stack multiple, optional filters in a tidy manner, using paths for filtering would require mapping every possible combination of filters into strict route templates.
*
*Part 4: Deep Nesting with Sub-Resources
*Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?
*•	Answer: The Separation of Concerns and Single Responsibility Principles are essential, and the Sub-Resource Locator pattern supports them. To prevent the SensorResource class from becoming unwieldy, we use the SensorReadingResource class for handling readings logic, making the code easier to understand. Having separate classes improves manageability and simplifies maintenance. This pattern also promotes code reuse, eliminating the need to rewrite logic in different application parts. Overall, it keeps the code clean and simple, enhancing developer efficiency and ease of collaboration.
*
*Part 5: Advanced Error Handling, Exception Mapping & Logging
*Question: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?
*•	Answer: The HTTP 404 Not Found error indicates that the requested website is unavailable. However, adding a new sensor to (/sensors) functions correctly. The HTTP 422 Unprocessable Entity error signifies that while the target website and format are correct, there are errors in the submitted information, such as using a non-existent Room ID from the database.
*Question: From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?
*•	Answer: Revealing details of Java program errors exposes sensitive information about your system, potentially allowing unauthorized individuals to access private data. This information can lead to identification of the software versions, system setup, and database in use, enabling malicious actors to pinpoint vulnerabilities. They may exploit known issues related to specific software versions to craft targeted attacks. Thus, caution is necessary when sharing error details to safeguard against potential threats to your system's security.
*Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?
*•	Answer: JAX-RS filters promote the Don't Repeat Yourself principle by centralizing logging. They simplify the application by automatically logging all requests and responses without cluttering business logic. Filters act as a protective layer that ensures consistent logging formats and separation from application logic, making updates easier and maintaining clean, organized code.
*
