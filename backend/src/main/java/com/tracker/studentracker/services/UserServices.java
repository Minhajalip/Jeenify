package com.tracker.studentracker.services;

import com.tracker.studentracker.models.User;
import com.tracker.studentracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServices {

    @Autowired
    public UserRepository userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  

    @Transactional
    public Long registerStudentUser(String name, String email, String password) {

        if(userRepo.existsByEmail(email)){
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));  
        user.setRole("STUDENT");

        User savedUser = userRepo.save(user);
        return savedUser.getId();
    }
}