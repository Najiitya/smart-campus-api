/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.config;

/**
 *
 * @author nethw
 */

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

// This is the missing piece! It makes every link start with /api/v1
@ApplicationPath("/api/v1")
public class ApplicationConfig extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        
        // Add your API Resources here
        resources.add(com.coursework.resources.DiscoveryResource.class);
        resources.add(com.coursework.resources.SensorRoomResource.class);
        resources.add(com.coursework.resources.SensorResource.class);
        resources.add(com.coursework.resources.SensorReadingResource.class);
        
        // Add your Exception Mappers here
        resources.add(com.coursework.mapper.GlobalExceptionMapper.class);
        resources.add(com.coursework.mapper.LinkedResourceNotFoundMapper.class);
        resources.add(com.coursework.mapper.RoomNotEmptyMapper.class);
        resources.add(com.coursework.mapper.SensorUnavailableMapper.class);
        
        // Add your Logging Filter here
        resources.add(com.coursework.filter.LoggingFilter.class);
        
        return resources;
    }
}