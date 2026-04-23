/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.mapper;

/**
 *
 * @author nethw
 */


import com.coursework.exceptions.LinkedResourceNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

// @Provider turns this watcher on
@Provider
public class LinkedResourceNotFoundMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {
        // We create a clean JSON error message
        String jsonError = "{\"error\": \"" + exception.getMessage() + "\"}";

        return Response.status(422) // HTTP 422 Unprocessable Entity
                .entity(jsonError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
