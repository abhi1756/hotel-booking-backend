package com.hotel_booking.hotel_booking.Utility;

import com.hotel_booking.hotel_booking.Entities.Booking;
import com.hotel_booking.hotel_booking.Repository.BookingRepository;
import com.hotel_booking.hotel_booking.Repository.RoomRepository;
import com.hotel_booking.hotel_booking.Services.BookingServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class TimeUtility {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingServices bookingServices;

    public void BookingCleanupService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void releaseExpiredBookings() {
        List<Booking> expiredBookings = bookingRepository.findByEndTime(LocalDate.now());
        for (Booking booking : expiredBookings) {
            bookingServices.cancelBooking(booking.getId());
        }
    }
}
