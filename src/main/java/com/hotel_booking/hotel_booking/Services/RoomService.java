package com.hotel_booking.hotel_booking.Services;

import com.hotel_booking.hotel_booking.DTO.RoomBookingDTO;
import com.hotel_booking.hotel_booking.Entities.Hotel;
import com.hotel_booking.hotel_booking.Entities.Room_no;
import com.hotel_booking.hotel_booking.Repository.HotelRepository;
import com.hotel_booking.hotel_booking.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public ResponseEntity<?> saveRoom(RoomBookingDTO roomDTO) {
        try {
            Optional<Hotel> data = hotelRepository.findById(roomDTO.getHotel_id());
            if (!data.get().isActive()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Hotel not found with ID: " + roomDTO.getHotel_id());
            }
            if (data.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Hotel not found with ID: " + roomDTO.getHotel_id());
            }

            Hotel hotel = data.get();

            int totalClassic = roomDTO.getClassic_rooms();
            int totalPremium = roomDTO.getPremium_rooms();
            int floors = roomDTO.getFloor();

            int totalRooms = totalClassic + totalPremium;
            if (totalRooms % floors != 0) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Rooms can't be evenly distributed across floors");
            }

            int roomPerFloor = totalRooms / floors;
            int classicPerFloor = totalClassic / floors;
            int premiumPerFloor = totalPremium / floors;
            int remainingPremium = totalPremium % floors;

            for (int i = 0; i < floors; i++) {
                int index = 0;
                int clsRoom = 1, prmRoom = 1;
                while (index < roomPerFloor) {
                    if (clsRoom <= classicPerFloor) {
                        Room_no room = new Room_no();
                        room.setRoom_id("C" + i + "_" + clsRoom++);
                        room.setPrice(roomDTO.getClassic_price());
                        room.setFloor(i);
                        room.setHotel(hotel);
                        roomRepository.save(room);
                    } else if (prmRoom <= premiumPerFloor) {
                        Room_no room = new Room_no();
                        room.setRoom_id("P" + i + "_" + prmRoom++);
                        room.setPrice(roomDTO.getPremium_price());
                        room.setFloor(i);
                        room.setHotel(hotel);
                        roomRepository.save(room);
                    } else {
                        if (remainingPremium != 0) {
                            Room_no room = new Room_no();
                            room.setRoom_id("P" + i + "_" + prmRoom++);
                            room.setPrice(roomDTO.getPremium_price());
                            room.setFloor(i);
                            room.setHotel(hotel);
                            roomRepository.save(room);
                            remainingPremium--;
                        } else {
                            Room_no room = new Room_no();
                            room.setRoom_id("C" + i + "_" + clsRoom++);
                            room.setPrice(roomDTO.getClassic_price());
                            room.setFloor(i);
                            room.setHotel(hotel);
                            roomRepository.save(room);
                        }
                    }
                    index++;
                }
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("All rooms saved equally on each floor for Hotel: " + hotel.getHotelName());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving rooms: " + e.getMessage());
        }
    }

    public List<Room_no> getRooms(int id){
        Optional<Hotel> data = hotelRepository.findById(id);
        Hotel hotel = data.get();
        return hotel.getRooms();
    }

}