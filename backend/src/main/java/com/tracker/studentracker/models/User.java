package com.tracker.studentracker.models;

public class User {
    private Long userId;
    private String fullName;
    private String email;
    private String passwordHash;
    private String role;
    private String createdAt;

    public Long getId() { return userId; }
    public void setId(Long userId) { this.userId = userId; }

    public String getName() { return fullName; }
    public void setName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}