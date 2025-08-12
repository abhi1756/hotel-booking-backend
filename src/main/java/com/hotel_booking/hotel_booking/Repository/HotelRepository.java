package com.hotel_booking.hotel_booking.Repository;

import com.hotel_booking.hotel_booking.Entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> getByHotelName (String name);
}