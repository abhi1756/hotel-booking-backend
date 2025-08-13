package com.hotel_booking.hotel_booking.Services;

import com.hotel_booking.hotel_booking.DTO.BookingDTO;
import com.hotel_booking.hotel_booking.Entities.Booking;
import com.hotel_booking.hotel_booking.Entities.Hotel;
import com.hotel_booking.hotel_booking.Entities.Room_no;
import com.hotel_booking.hotel_booking.Repository.BookingRepository;
import com.hotel_booking.hotel_booking.Repository.HotelRepository;
import com.hotel_booking.hotel_booking.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookingServices {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelRepository hotelRepository;

    public ResponseEntity<?> bookroom(BookingDTO userdata) {
        try {
            Optional<Hotel> data = hotelRepository.findById(userdata.getHotel_id());
            if (!data.get().isActive()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Hotel not found with ID: " + userdata.getHotel_id());
            }
            List<Room_no> allRooms = roomService.getRooms(userdata.getHotel_id());
            if (allRooms.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Rooms Found");
            }
            List<String> requestedRoomIds = userdata.getRooms();
            List<String> bookedRooms = new ArrayList<>();
            double total_price = 0;

            for (Room_no room : allRooms) {
                if (requestedRoomIds.contains(room.getRoom_id())) {
                    if (room.isBooked()) {
                        return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body("Room not available");
                    }
                    room.setBooked(true);
                    total_price += room.getPrice();
                    bookedRooms.add(room.getRoom_id());
                    roomRepository.save(room);
                }
            }

            Booking booking = new Booking();
            booking.setHotel_id(userdata.getHotel_id());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            booking.setUserEmail(username);
            booking.setPrice(total_price);
            booking.setRooms(bookedRooms);
            booking.setStartTime(userdata.getStartDate());
            booking.setEndTime(userdata.getEndDate());

            bookingRepository.save(booking);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Booked Successfully!");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid request: " + e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Booking failed: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getBookingData() {
        try {
            List<Booking> bookings = bookingRepository.findAll();

            if (bookings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT) // 204
                        .body("No bookings found");
            }

            return ResponseEntity.status(HttpStatus.OK) // 200
                    .body(bookings);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Failed to fetch bookings: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getUserBookingData() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            List<Booking> bookings = bookingRepository.findByUserEmail(username);

            if (bookings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT) // 204
                        .body("No bookings found");
            }

            return ResponseEntity.status(HttpStatus.OK) // 200
                    .body(bookings);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Failed to fetch bookings: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> cancelBooking(int id) {
        try {
            Optional<Booking> userData = bookingRepository.findById(id);

            if (userData.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND) // 404
                        .body("Booking not found for ID: " + id);
            }

            int hotelId = userData.get().getHotel_id();
            List<Room_no> allRooms = roomService.getRooms(hotelId);
            if (allRooms.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Rooms Found");
            }
            List<String> rooms = userData.get().getRooms();

            double refundAmount = 0;
            for (Room_no room : allRooms) {
                if (rooms.contains(room.getRoom_id()) && room.isBooked()) {
                    room.setBooked(false);
                    refundAmount += room.getPrice();
                    roomRepository.save(room);
                }
            }

            bookingRepository.deleteById(id);

            return ResponseEntity
                    .status(HttpStatus.OK) // 200
                    .body("Booking cancelled successfully for ID: " + id + "Your Refund Amount is " + refundAmount);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Error while cancelling booking: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> cancel_room(Booking data) {
        try {
            Optional<Booking> userData = bookingRepository.findById(data.getId());

            if (userData.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND) // 404
                        .body("Booking not found for ID: " + data.getId());
            }

            int hotelId = userData.get().getHotel_id();
            double totalAmount = userData.get().getPrice();

            double refundAmount = 0;

            List<Room_no> allRooms = roomService.getRooms(hotelId);
            if (allRooms.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Rooms Found");
            }

            for (Room_no room : allRooms) {
                if (data.getRooms().contains(room.getRoom_id()) && room.isBooked()) {
                    room.setBooked(false);
                    userData.get().getRooms().remove(room.getRoom_id());
                    data.getRooms().remove(room.getRoom_id());
                    refundAmount += room.getPrice();
                    totalAmount -= room.getPrice();
                    roomRepository.save(room);
                }

                if (userData.get().getRooms().isEmpty()) {
                    bookingRepository.deleteById(data.getId());
                }else {
                    userData.get().setPrice(totalAmount);
                    bookingRepository.save(userData.get());
                }

            }
            if (!data.getRooms().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST) // 400
                        .body("Wrong Rooms");
            }

            return ResponseEntity
                    .status(HttpStatus.OK) // 200
                    .body("Booking cancelled successfully for ID: " + data.getId() + "Your Refund Amount is " + refundAmount);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 500
                    .body("Error while cancelling booking: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error occurred: " + e.getMessage());
        }
    }
}