package com.example.demoSQL.controller;

import com.example.demoSQL.dto.ApiResponse;
import com.example.demoSQL.dto.request.AuthenticationRequest;
import com.example.demoSQL.dto.response.AuthenticationResponse;
import com.example.demoSQL.service.AuthenticationService;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Builder
public class AuthenticationController {
    AuthenticationService authenticationService;
    private PathPatternRequestMatcher.Builder builder;

    @PostMapping("Log-in")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authenticationRequest) {
        boolean result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
}