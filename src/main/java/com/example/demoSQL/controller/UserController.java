package com.example.demoSQL.controller;
import com.example.demoSQL.dto.UserCreationRequest;
import com.example.demoSQL.dto.UserUpdateRequest;

import com.example.demoSQL.dto.response.UserResponse;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/user")
    public User addUser(@Valid @RequestBody UserCreationRequest userCreationRequest) {
        return userService.addUser(userCreationRequest);
    }
    @GetMapping("/user")
    public List<User> getAllUser(){
        return userService.getAllUsers();
    }
    @GetMapping("/user/{Id}")
    public UserResponse getUserById(@PathVariable Long Id) {
        return userService.getUserById(Id);
    }
    @PutMapping("/user/{Id}")
    public User updateUser(@PathVariable Long Id , @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(Id, userUpdateRequest);
    }
    @DeleteMapping("/user/{Id}")
    public void deleteUser(@PathVariable Long Id) {
        userService.deleteUserById(Id);
    }


}
