/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.mapper;

/**
 *
 * @author nethw
 */


import com.coursework.exceptions.SensorUnavailableException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class SensorUnavailableMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException exception) {
        
        String jsonError = "{\"error\": \"" + exception.getMessage() + "\"}";

        return Response.status(Response.Status.FORBIDDEN) // HTTP 403
                .entity(jsonError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
