package com.example.demoSQL.service;

import com.example.demoSQL.dto.request.UserCreationRequest;
import com.example.demoSQL.dto.request.UserUpdateRequest;
import com.example.demoSQL.dto.response.UserResponse;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.enums.Role;
import com.example.demoSQL.globalexceptionhandler.AppException;
import com.example.demoSQL.globalexceptionhandler.ErrorCode;
import com.example.demoSQL.mapper.UserMapper;
import com.example.demoSQL.repository.RoleRepository;
import com.example.demoSQL.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    BCryptPasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse addUser(UserCreationRequest userCreationRequest) {
        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
      //  user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toUserResponse(user))
                .toList();
    }

    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(userUpdateRequest, user);
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public UserResponse findByName(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    public UserResponse searchUser(String username, String email, String firstName, String lastName) {
        if (username != null && email != null) {
            User user = userRepository.findByUsernameAndEmail(username, email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            return userMapper.toUserResponse(user);
        } else if (username != null) {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            return userMapper.toUserResponse(user);
        } else if (email != null) {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            return userMapper.toUserResponse(user);
        }
        throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

    public UserResponse getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
