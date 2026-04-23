/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.mapper;

/**
 *
 * @author nethw
 */

import com.coursework.exceptions.RoomNotEmptyException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

// @Provider tells the server to turn this watcher on
@Provider
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {
        // We create a clean JSON error message
        String jsonError = "{\"error\": \"" + exception.getMessage() + "\"}";

        return Response.status(Response.Status.CONFLICT) // This is HTTP 409
                .entity(jsonError)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
