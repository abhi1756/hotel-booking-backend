package com.hotel_booking.hotel_booking.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomBookingDTO {
    private int floor;
    private int classic_rooms;
    private int premium_rooms;
    private double classic_price;
    private double premium_price;

    private int hotel_id;
}
