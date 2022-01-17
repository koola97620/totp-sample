package com.example.auth.user.ui;

import com.example.auth.user.application.UserService;
import com.example.auth.user.domain.User;
import com.example.auth.user.dto.UserRegistRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/add")
    public User registUser(@RequestBody UserRegistRequest request) {
        User user = userService.register(request.getUsername(), request.getPassword());
        return user;
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id")Long id) {
        return userService.getUser(id);
    }
}
