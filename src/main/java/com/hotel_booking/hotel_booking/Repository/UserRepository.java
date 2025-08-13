package com.hotel_booking.hotel_booking.Repository;

import com.hotel_booking.hotel_booking.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> getUserByEmail(String email);
}