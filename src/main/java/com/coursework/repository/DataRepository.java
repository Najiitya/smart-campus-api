/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.repository;

/**
 *
 * @author nethw
 */
import com.coursework.model.Room;
import com.coursework.model.Sensor;
import com.coursework.model.SensorReading;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DataRepository {
    
    //room database
    public static Map<String, Room> rooms = new HashMap<>();
    
    //sensor database
    public static Map<String, Sensor> sensors = new HashMap<>();
    
    //sensor reading
    public static Map<String, List<SensorReading>> readings = new HashMap<>();
    
    
}
