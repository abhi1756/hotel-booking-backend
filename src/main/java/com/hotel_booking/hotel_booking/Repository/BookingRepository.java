package com.hotel_booking.hotel_booking.Repository;

import com.hotel_booking.hotel_booking.Entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByEndTime(LocalDate now);
    List<Booking> findByUserEmail(String useremail);
}
