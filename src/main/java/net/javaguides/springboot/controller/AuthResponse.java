package net.javaguides.springboot.controller;

public class AuthResponse {
    private String token;
    private long expiresAt; // epoch time in ms

    public AuthResponse(String token, long expiresAt) {
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
