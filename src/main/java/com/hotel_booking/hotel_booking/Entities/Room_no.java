package com.hotel_booking.hotel_booking.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room_no {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String room_id;
    private int floor;
    private double price;
    private boolean isBooked = false;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonBackReference
    @ToString.Exclude
    private Hotel hotel;

}