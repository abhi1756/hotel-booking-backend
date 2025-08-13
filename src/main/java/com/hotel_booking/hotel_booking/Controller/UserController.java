package com.hotel_booking.hotel_booking.Controller;

import com.hotel_booking.hotel_booking.DTO.UserDTO;
import com.hotel_booking.hotel_booking.Entities.User;
import com.hotel_booking.hotel_booking.SecurityConfig.JWTUtility;
import com.hotel_booking.hotel_booking.SecurityConfig.UserDetailService;
import com.hotel_booking.hotel_booking.Services.UserServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("/users")
@Slf4j
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private UserDetailService userDetailService;

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
    public ResponseEntity<?> login(@RequestBody User user) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());
            String jwt = jwtUtility.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}