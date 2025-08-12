package com.hotel_booking.hotel_booking.Repository;

import com.hotel_booking.hotel_booking.Entities.Room_no;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room_no, Integer> {
}
