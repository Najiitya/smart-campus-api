/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.mapper;

/**
 *
 * @author nethw
 */

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

// @Provider turns this global watcher on
@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        // 1. Print the real error in your terminal so YOU can fix it later
        exception.printStackTrace();

        // 2. Hide the real error from the user to keep the API safe
        String jsonError = "{\"error\": \"An unexpected internal server error occurred.\"}";

        // 3. Return a standard 500 error
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // HTTP 500
                .entity(jsonError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
