package com.workintech.twitter.controller;

import com.workintech.twitter.entity.User;
import com.workintech.twitter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user){
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAll();
    }

}
