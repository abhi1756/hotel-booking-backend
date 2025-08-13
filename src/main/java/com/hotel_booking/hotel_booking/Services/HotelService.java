package com.hotel_booking.hotel_booking.Services;

import com.hotel_booking.hotel_booking.Entities.Hotel;
import com.hotel_booking.hotel_booking.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class HotelService {
    @Autowired
    public HotelRepository hotelRepository;

    public ResponseEntity<?> createHotel(Hotel hotel) {
        try {
            Hotel savedHotel = hotelRepository.save(hotel);
            return ResponseEntity
                    .status(HttpStatus.CREATED) // 201
                    .body(savedHotel);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Error while creating hotel: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deactivateHotel(int id) {
        try {
            Optional<Hotel> optionalHotel = hotelRepository.findById(id);
            if (optionalHotel.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND) // 404
                        .body("Hotel not found with id: " + id);
            }

            Hotel hotel = optionalHotel.get();
            hotel.setActive(false);
            Hotel updatedHotel = hotelRepository.save(hotel);

            return ResponseEntity
                    .status(HttpStatus.OK) // 200
                    .body(updatedHotel);
        }
        catch (RuntimeException e){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN) // 500
                    .body("Unauthorised Request: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Error while deactivating hotel: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getAllHotel() {
        try {
            List<Hotel> hotels = hotelRepository.findAll();

            if (hotels.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NO_CONTENT) // 204
                        .body("No hotels found");
            }

            return ResponseEntity
                    .status(HttpStatus.OK) // 200
                    .body(hotels);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Error fetching hotels: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getHotelByName(String name) {
        try {
            List<Hotel> hotels = hotelRepository.getByHotelName(name);

            if (hotels.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND) // 404
                        .body("No hotels found with name: " + name);
            }

            return ResponseEntity
                    .status(HttpStatus.OK) // 200
                    .body(hotels);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Error fetching hotel by name: " + e.getMessage());
        }
    }
}