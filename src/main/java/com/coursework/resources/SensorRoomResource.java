package com.coursework.resources;

import com.coursework.model.Room;
import com.coursework.repository.DataRepository;
import com.coursework.exceptions.RoomNotEmptyException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

// FIXED: Changed to "/rooms" to match your Discovery API links
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorRoomResource {
    
    @GET
    public List<Room> getallRooms(){
        return new ArrayList<>(DataRepository.rooms.values());
    }
    
    @POST
    public Response createRoom(Room room){
        DataRepository.rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }
    
    @GET
    @Path("/{roomId}")
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = DataRepository.rooms.get(roomId);
        
        // Check if the room does not exist
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Error: Room not found.")
                           .build();
        }
        
        
        
        return Response.ok(room).build();
    }
    
    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataRepository.rooms.get(roomId);
        
        
        if(room == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        
        if (!room.getSensorIds().isEmpty()) {
            // Throw new custom exception!
            throw new RoomNotEmptyException(
                "Cannot delete. Room currently has active hardware."
            );
        }
        
        DataRepository.rooms.remove(roomId);
        return Response.noContent().build();
    }
    @GET
    @Path("/crash")
    public Response triggerCrash() {
        // This simulates a random, unexpected server failure
        throw new RuntimeException("Simulated catastrophic failure!");
    }
}