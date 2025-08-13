package com.hotel_booking.hotel_booking.Controller;

import com.hotel_booking.hotel_booking.Entities.Hotel;
import com.hotel_booking.hotel_booking.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    public HotelService hotelService;

    @PostMapping("/create")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel){
        return hotelService.createHotel(hotel);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return hotelService.deactivateHotel(id);
    }

    @GetMapping("/allhotels")
    public ResponseEntity<?> allHotels(){
        return hotelService.getAllHotel();
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getHotels(@PathVariable String name){
        return hotelService.getHotelByName(name);
    }
}
