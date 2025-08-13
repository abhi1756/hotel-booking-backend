package com.hotel_booking.hotel_booking.Services;

import com.hotel_booking.hotel_booking.DTO.UserDTO;
import com.hotel_booking.hotel_booking.Entities.User;
import com.hotel_booking.hotel_booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserServices{
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();

    public String saveuser(UserDTO user) {
        System.out.println(user + "useeeeeeeee");
        if (userRepository.getUserByEmail(user.getEmail()).isPresent()) return "Already Exists.";
        else {
            User user1 = new User();
            user1.setEmail(user.getEmail());
            user1.setPassword(passwordencoder.encode(user.getPassword()));
            user1.setAdmin(user.isAdmin());
            user1.setName(user.getName());
            userRepository.save(user1);
            System.out.println(user1+" user11111111");
        }
        System.out.println(user+"userrrrr");
        return "Successfully Added.";
    }

    public Optional<User> getuser(int id) {
        return userRepository.findById(id);
    }

    public String deleteuser(int id) {
        userRepository.deleteById(id);
        return "Successfully Deleted";
    }

    public String loginUser(String email, String password) {
        Optional<User> data = userRepository.getUserByEmail(email);
        if (data.isPresent()) {
            if (passwordencoder.matches(password, data.get().getPassword()))return "login";
            else return "Wrong Credentials";
        }
        return "User Not found";
    }
}