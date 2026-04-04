package com.tracker.studentracker.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AuthHelper {

    public Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            Map<String, Object> details = (Map<String, Object>) auth.getDetails();
            return (Long) details.get("userId");
        }
        throw new RuntimeException("Not authenticated");
    }

    public String getCurrentRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken) {
            Map<String, Object> details = (Map<String, Object>) auth.getDetails();
            return (String) details.get("role");
        }
        throw new RuntimeException("Not authenticated");
    }
}