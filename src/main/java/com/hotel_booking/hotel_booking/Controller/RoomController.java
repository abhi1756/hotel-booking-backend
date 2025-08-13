package com.hotel_booking.hotel_booking.Controller;

import com.hotel_booking.hotel_booking.DTO.RoomBookingDTO;
import com.hotel_booking.hotel_booking.Entities.Room_no;
import com.hotel_booking.hotel_booking.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/addRooms")
    public ResponseEntity<?> save(@RequestBody RoomBookingDTO room){
        return roomService.saveRoom(room);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable int id){
        try{
            List<Room_no> res = roomService.getRooms(id);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
