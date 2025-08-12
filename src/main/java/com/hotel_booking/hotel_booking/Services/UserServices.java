package com.hotel_booking.hotel_booking.Services;

import com.hotel_booking.hotel_booking.Entities.User;
import com.hotel_booking.hotel_booking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public String saveuser(User user) {
        if (userRepository.getUserByEmail(user.getEmail()).size() != 0) return "Already Exists.";
        else {
            userRepository.save(user);
        }
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
        List<User> data = userRepository.getUserByEmail(email);
        if (data.size() != 0) {
            if (password.equals(data.get(0).getPassword())) return "login";
            else return "Wrong Credentials";
        }
        return "User Not found";
    }
}