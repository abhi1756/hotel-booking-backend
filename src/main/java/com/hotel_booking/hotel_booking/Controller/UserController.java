package com.hotel_booking.hotel_booking.Controller;

import com.hotel_booking.hotel_booking.DTO.UserDTO;
import com.hotel_booking.hotel_booking.Entities.User;
import com.hotel_booking.hotel_booking.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping
    public String save(@RequestBody UserDTO user) {
        return userServices.saveuser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> get(@PathVariable int id) {
        return userServices.getuser(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        return userServices.deleteuser(id);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userServices.loginUser(user.getEmail(), user.getPassword());
    }
}