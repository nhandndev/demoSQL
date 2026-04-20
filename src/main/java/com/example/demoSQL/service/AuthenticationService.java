package com.example.demoSQL.service;

import com.example.demoSQL.dto.request.AuthenticationRequest;
import com.example.demoSQL.globalexceptionhandler.AppException;
import com.example.demoSQL.globalexceptionhandler.ErrorCode;
import com.example.demoSQL.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    public boolean authenticate(AuthenticationRequest request){
        var User = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(request.getPassword(),User.getPassword());
    }
}
