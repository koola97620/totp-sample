package com.example.auth.user.dto;

public class UserRegistRequest {
    private String username;
    private String password;

    protected UserRegistRequest() {}

    public UserRegistRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
