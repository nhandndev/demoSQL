package com.example.demoSQL.controller;

import com.example.demoSQL.dto.ApiResponse;
import com.example.demoSQL.dto.request.AuthenticationRequest;
import com.example.demoSQL.dto.response.AuthenticationResponse;
import com.example.demoSQL.service.AuthenticationService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Builder
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("Log-in")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}