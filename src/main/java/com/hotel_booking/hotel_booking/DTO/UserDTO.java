package com.hotel_booking.hotel_booking.DTO;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean isAdmin;
}
