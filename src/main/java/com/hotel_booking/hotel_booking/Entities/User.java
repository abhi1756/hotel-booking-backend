package com.hotel_booking.hotel_booking.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean isAdmin;

//    public void setIsAdmin(boolean isAdmin){
//        this.isAdmin = isAdmin;
//    }
//    public boolean getIsAdmin(){
//       return isAdmin;
//    }
}