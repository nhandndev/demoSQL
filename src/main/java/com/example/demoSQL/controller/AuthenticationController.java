package com.example.demoSQL.controller;

import com.example.demoSQL.dto.ApiResponse;
import com.example.demoSQL.dto.request.AuthenticationRequest;
import com.example.demoSQL.dto.request.IntrospectRequest;
import com.example.demoSQL.dto.response.AuthenticationResponse;
import com.example.demoSQL.dto.response.IntrospectResponse;
import com.example.demoSQL.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@Builder
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest introspectRequest)
    throws ParseException , JOSEException {
        var result = authenticationService.introspect(introspectRequest);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}