package com.hotel_booking.hotel_booking.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int hotel_id;
    private String user_email;
    private double price;

    private LocalDate startTime;
    private LocalDate endTime;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> rooms;

}