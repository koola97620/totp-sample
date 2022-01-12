package com.example.auth.core.user.ui;

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
