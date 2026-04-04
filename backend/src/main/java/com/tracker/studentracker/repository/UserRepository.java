package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.User;  // singular User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);  // used by AuthService
}