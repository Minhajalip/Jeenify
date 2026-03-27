package com.tracker.studentracker.models;

public class Users {

    public enum Role{
        ADMIN,
        TEACHER,
        STUDENT
    }
    private int userId;
    private String name;
    private String email;
    private String passwordHash;
    private String createdAt;
    private Role role;

    public int getUserId() {return this.userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public String getPasswordHash() {return this.passwordHash;}
    public void setPasswordHash(String passwordHash) {this.passwordHash = passwordHash;}

    public String getDate() {return this.createdAt;}
    public void setDate(String createdAt) {this.createdAt = createdAt;}

    public Role getRole() {return this.role;}
    public void setRole(Role role) {this.role = role;}
}
