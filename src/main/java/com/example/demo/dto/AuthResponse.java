package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private Long userId;
    private String email;
    private String role;

    // No-args constructor
    public AuthResponse() {}

    public AuthResponse(String token, Long userId, String email, String role) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }

    // Optional constructor for error responses
    public AuthResponse(String token, Long userId, String email, String role, String message) {
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.role = role;
        // message field not needed in success response, but can be used if extended
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}