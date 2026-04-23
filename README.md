# Smart Campus Sensor & Room Management API

> A RESTful web service for managing university IoT infrastructure — rooms, sensors, and readings.

---

## Table of Contents

1. [Overview](#overview)
2. [Build & Launch](#build--launch)
3. [Sample cURL](#sample-curl)
4. [Report Q&A](#report-qa)
   - [Part 1 — Service Architecture & Setup](#part-1--service-architecture--setup)
   - [Part 2 — Room Management](#part-2--room-management)
   - [Part 3 — Sensor Operations & Linking](#part-3--sensor-operations--linking)
   - [Part 4 — Deep Nesting with Sub-Resources](#part-4--deep-nesting-with-sub-resources)
   - [Part 5 — Error Handling, Exception Mapping & Logging](#part-5--error-handling-exception-mapping--logging)

---

## Overview

Built with Java and JAX-RS (Jersey), this API manages university infrastructure by tracking rooms and IoT sensors (CO₂ monitors, temperature gauges). It uses an in-memory data structure for fast operations, deployed via an embedded Grizzly HTTP server.

**Key features:**
- Resource nesting (sub-resources for sensor readings)
- Query parameter filtering
- Custom exception mappers for a secure, leak-proof architecture

---

## Build & Launch

### Prerequisites

- Java JDK 11 or higher
- Apache Maven
- IDE such as Apache NetBeans

### Steps

1. Clone the repository to your local machine.
2. Open the `CourseWork` project folder in your IDE.
3. Run a clean build — click **Clean and Build** in NetBeans, or run:
   ```bash
   mvn clean package
   ```
4. Navigate to `src/main/java/com/coursework/Main.java`.
5. Run `Main.java`. Look for the console message:
   ```
   SMART CAMPUS SERVER IS RUNNING!
   ```
6. The base API is now accessible at:
   ```
   http://localhost:8080/api/v1/
   ```

---

## Sample cURL

**View API Discovery Metadata:**
```bash
curl -X GET http://localhost:8080/api/v1/
```

---

## Report Q&A

---

### Part 1 — Service Architecture & Setup

---

**Q: What is the default lifecycle of a JAX-RS resource class — per-request or singleton? How does this affect in-memory data management?**

By default, JAX-RS creates a new instance of each resource class (e.g. `RoomResource`) for every incoming HTTP request and discards it once the response is sent. This means instance-level fields cannot hold state between requests. To preserve data without a database, a static structure like a `public static Map` inside a `DataRepository` class must be used — this persists across requests regardless of how many resource instances are created.

---

**Q: Why is HATEOAS considered a hallmark of advanced REST design, and how does it benefit client developers over static documentation?**

HATEOAS (Hypermedia as the Engine of Application State) embeds navigable links directly in API responses, so clients can discover next actions without consulting external docs. Even if backend URLs change, clients remain stable by following links from the discovery endpoint. This keeps the client and server decoupled, making the API more flexible and easier to evolve over time.

---

### Part 2 — Room Management

---

**Q: What are the trade-offs of returning only IDs versus full room objects when listing rooms?**

Returning only IDs reduces response size and network traffic, but forces the client to make additional requests to retrieve full details. Returning complete room objects increases initial bandwidth but provides everything in one round-trip, improving perceived performance and user experience. The right choice depends on how often clients need full details versus just the identifiers.

---

**Q: Is the DELETE operation idempotent in this implementation? What happens on repeated DELETE requests for the same room?**

Yes, DELETE is idempotent. The first successful request returns `204 No Content` and removes the room. Subsequent identical requests return `404 Not Found` because the resource no longer exists. The server state does not change after the first deletion, satisfying the idempotency requirement — repeated calls produce the same end-state.

---

### Part 3 — Sensor Operations & Linking

---

**Q: What happens if a client sends data in the wrong format (e.g. `text/plain` instead of `application/json`) to a `@Consumes(APPLICATION_JSON)` endpoint?**

JAX-RS intercepts the request before it reaches your method and rejects it immediately, returning `HTTP 415 Unsupported Media Type`. The `@Consumes` annotation acts as a gatekeeper — if the incoming `Content-Type` header doesn't match, the request is refused without any business logic executing.

---

**Q: Why is `@QueryParam` preferred over embedding filter values in the URL path (e.g. `/sensors/type/CO2`)?**

URL path segments are designed to identify specific hierarchical resources. Query parameters are designed for optional modifiers like filters and sorts. Using path segments for filtering creates rigid route templates for every possible filter combination. Query parameters are flexible, optional, and composable — multiple filters can be stacked cleanly without multiplying route definitions.

---

### Part 4 — Deep Nesting with Sub-Resources

---

**Q: What are the architectural benefits of the Sub-Resource Locator pattern versus defining all nested paths in one large controller?**

The Sub-Resource Locator pattern applies the Single Responsibility Principle — readings logic lives in `SensorReadingResource` rather than bloating `SensorResource`. Smaller, focused classes are easier to read, test, and maintain. The pattern also enables code reuse: the same sub-resource class can be referenced from multiple parent resources without duplicating logic.

---

### Part 5 — Error Handling, Exception Mapping & Logging

---

**Q: Why is `HTTP 422` more semantically accurate than `404` when a JSON payload references a non-existent resource?**

`404 Not Found` signals that the endpoint URL itself doesn't exist. `422 Unprocessable Entity` signals that the URL is correct and the format is valid, but the data inside the request is logically invalid — for example, referencing a Room ID that doesn't exist in the database. Using 422 gives the client a much more precise error message about what actually went wrong.

---

**Q: What security risks come from exposing internal Java stack traces to external API consumers?**

Stack traces can reveal the software framework, library versions, class names, and internal architecture of the application. Attackers can use this to look up known CVEs for specific versions and craft targeted exploits. A well-designed API should return only generic, user-safe error messages externally while logging the full trace internally — this is the purpose of custom exception mappers.

---

**Q: Why use JAX-RS filters for cross-cutting concerns like logging instead of adding `Logger.info()` calls to every resource method?**

Filters centralize logging in one place, following the DRY (Don't Repeat Yourself) principle. Business logic stays clean and focused. All requests and responses are logged consistently in a uniform format without any risk of a developer forgetting to add a log statement. When the logging format needs to change, only one class needs updating.

---