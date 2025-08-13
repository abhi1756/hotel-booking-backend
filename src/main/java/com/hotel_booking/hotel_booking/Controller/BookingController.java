package com.hotel_booking.hotel_booking.Controller;

import com.hotel_booking.hotel_booking.DTO.BookingDTO;
import com.hotel_booking.hotel_booking.Entities.Booking;
import com.hotel_booking.hotel_booking.Services.BookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingServices bookingServices;

    @PostMapping
    public ResponseEntity<?> book_room(@RequestBody BookingDTO booking){
        return bookingServices.bookroom(booking);
    }

    @GetMapping("/allbooking")
    public ResponseEntity<?> getData(){
        return bookingServices.getBookingData();
    }

    @GetMapping("/userBooking")
    public ResponseEntity<?> getUserBooking(){
        return bookingServices.getUserBookingData();
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> cancel(@PathVariable int id){
        return bookingServices.cancelBooking(id);
    }

    @PostMapping("/cancelRoom")
    public ResponseEntity<?> cancelSpecificroom(@RequestBody Booking booking){
        return bookingServices.cancel_room(booking);
    }
}