package com.example.auth.core.user.ui;

import com.example.auth.core.user.application.UserService;
import com.example.auth.core.user.domain.User;
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
