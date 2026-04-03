package com.tracker.studentracker.repository;

import com.tracker.studentracker.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
//import java.sql.Connection;
//import java.sql.SQLException;

//import org.springframework.jdbc.core.PreparedStatementCreator;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public int save(Users user){
        String sql = "INSERT INTO users(full_name, email, password_hash, role) VALUES(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            // Use the Statement constant for better compatibility
            PreparedStatement ps = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash()); // Plain text as requested
            ps.setString(4, user.getRole().name());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public boolean existsByEmail(String email){
        String sql = "SELECT COUNT(*) FROM users WHERE email=?";
        Integer count = jdbc.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
