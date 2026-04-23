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
import com.coursework.model.SensorStatus;
import com.coursework.model.SensorReading;
import com.coursework.repository.DataRepository;
import com.coursework.exceptions.SensorUnavailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    
    @GET
    public List<SensorReading> getHistory() {
        return DataRepository.readings.getOrDefault(sensorId, new ArrayList<>());
    }

     
    @POST
    public Response addReading(SensorReading reading) {
        Sensor mainSensor = DataRepository.sensors.get(sensorId);
        
        if (mainSensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Error: Sensor does not exist.")
                           .build();
        }
        
        if (mainSensor.getStatus() == SensorStatus.MAINTENANCE) {
            // Throw our new custom exception!
            throw new SensorUnavailableException(
                "Error: Sensor is physically disconnected and cannot accept readings."
            );
        }

        
        List<SensorReading> history = DataRepository.readings.getOrDefault(sensorId, new ArrayList<>());
        history.add(reading);
        DataRepository.readings.put(sensorId, history);

        
        mainSensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}
