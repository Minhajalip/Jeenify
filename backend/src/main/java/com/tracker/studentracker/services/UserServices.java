package com.tracker.studentracker.services;

import com.tracker.studentracker.models.Users;
import com.tracker.studentracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServices {

    @Autowired
    public UserRepository userRepo;

    @Transactional
    public int registerStudentUser(String name, String email, String password) {

        if(userRepo.existsByEmail(email)){
            throw new RuntimeException("Email already exists");
        }

        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(password);
        user.setRole(Users.Role.STUDENT);

        return userRepo.save(user);
    }

}
