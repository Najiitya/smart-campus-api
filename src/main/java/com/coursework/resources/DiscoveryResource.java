/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resources;

/**
 *
 * @author nethw
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {
    
    @GET
    public Response getApiInfo(){
        Map<String, Object> info = new HashMap<>();
        info.put("api_name", "Smart Campus API");
        info.put("version", "v1");
        info.put("admin_contact", "your.email@westminster.ac.uk");
        
        Map<String, String> links = new HashMap<>();
        links.put("rooms", "/api/v1/rooms");
        links.put("sensors", "/api/v1/sensors");
        
        info.put("links", links);

        return Response.ok(info).build();
    }
}
