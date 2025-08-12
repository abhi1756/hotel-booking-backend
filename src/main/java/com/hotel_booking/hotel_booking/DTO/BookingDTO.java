package com.hotel_booking.hotel_booking.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class BookingDTO {
    private int hotel_id;
    private String user_email;
    private List<String> rooms;
    private LocalDate startDate;
    private LocalDate endDate;
}
