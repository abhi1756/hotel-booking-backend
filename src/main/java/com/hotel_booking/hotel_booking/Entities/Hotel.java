package com.hotel_booking.hotel_booking.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String hotelName;
    private String location;
    private String description;
    private double rating;
    private String contacts;
//    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isActive = true;
    private int total_person;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Room_no> rooms = new ArrayList<>();
}