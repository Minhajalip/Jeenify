package com.tracker.studentracker.dto;

import com.tracker.studentracker.models.Role;

public class StaffRegisterRequest {
    private String name;
    private String email;
    private String password;
    private String teacherNumber;
    private Role role;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTeacherNumber() { return teacherNumber; }
    public void setTeacherNumber(String teacherNumber) { this.teacherNumber = teacherNumber; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}