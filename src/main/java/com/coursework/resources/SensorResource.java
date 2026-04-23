/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.resources;

/**
 *
 * @author nethw
 */

import com.coursework.model.Sensor;
import com.coursework.model.SensorType;
import com.coursework.repository.DataRepository;
import com.coursework.exceptions.LinkedResourceNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.ArrayList;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SensorResource {
    @GET
    public Response getSensors(@QueryParam("type") SensorType type){
        List<Sensor> sensors = new ArrayList<>(DataRepository.sensors.values());
        
        if(type == null){
            return Response.ok(sensors).build();
        }
        
        List<Sensor> filteredSensors = new ArrayList<>();
        for(Sensor s : sensors){
            if(s.getType() == type){
                filteredSensors.add(s);
            }
        }
        return Response.ok(filteredSensors).build();
    }
    
    @POST
    public Response addSensor(Sensor sensor){
        if (!DataRepository.rooms.containsKey(sensor.getRoomId())) {
            // Returns a 422 error because the room is missing
            throw new LinkedResourceNotFoundException(
                "Error: The specified roomId does not exist in the system."
            );
        }
        
        DataRepository.sensors.put(sensor.getId(), sensor);
        
        DataRepository.rooms.get(sensor.getRoomId()).getSensorIds().add(sensor.getId());
        
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }
    
    @Path("/{sensorId}/read")
    public SensorReadingResource getReadingResource(@PathParam("sensorId") String sensorId){
        return new SensorReadingResource(sensorId);
    }
}
