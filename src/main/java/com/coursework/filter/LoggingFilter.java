/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.filter;

/**
 *
 * @author nethw
 */

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

// @Provider turns this logging camera on
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    // This is the Java logger the coursework asked you to use
    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    // 1. This runs when a request comes IN
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getAbsolutePath().toString();
        
        logger.info("INCOMING REQUEST: Method=" + method + ", URI=" + path);
    }

    // 2. This runs when a response goes OUT
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        int status = responseContext.getStatus();
        
        logger.info("OUTGOING RESPONSE: Status Code=" + status);
    }
}
