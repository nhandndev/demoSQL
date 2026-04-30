package com.example.demoSQL.controller;
import com.example.demoSQL.dto.ApiResponse;
import com.example.demoSQL.dto.request.UserCreationRequest;
import com.example.demoSQL.dto.request.UserUpdateRequest;

import com.example.demoSQL.dto.response.UserResponse;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/user")
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest)
    {
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.addUser(userCreationRequest))
                .build();
        return apiResponse;
    }
    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        authentication.getAuthorities().forEach(authority -> {
            log.info("authority : {}", authority);
        });
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
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
    @GetMapping("/user/username/{UserName}")
    public UserResponse getUserByUserName(@PathVariable String UserName) {
        return userService.findByName(UserName);
    }
    @GetMapping("/user/username/search")
    public UserResponse getUserByName(
            @RequestParam(required = false) String UserName,
            @RequestParam(required = false) String Email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        return userService.searchUser(UserName, Email, firstName, lastName);
    }
    @GetMapping("/user/my-ìno")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .result(userService.getMyInfo())
                .build();
    }

}
