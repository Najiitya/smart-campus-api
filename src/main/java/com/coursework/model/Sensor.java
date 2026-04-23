/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coursework.model;

public class Sensor {
    private String id;
    private SensorType type;
    private SensorStatus status;
    private double currentValue;
    private String roomId;

    // 1. Empty Constructor
    public Sensor() {}

    // 2. Full Constructor
    public Sensor(String id, SensorType type, SensorStatus status, double currentValue, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentValue = currentValue;
        this.roomId = roomId;
    }

    // 3. Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public SensorType getType() { return type; }
    public void setType(SensorType type) { this.type = type; }

    public SensorStatus getStatus() { return status; }
    public void setStatus(SensorStatus status) { this.status = status; }

    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
}