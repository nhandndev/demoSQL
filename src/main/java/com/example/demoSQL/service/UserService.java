package com.example.demoSQL.service;
import com.example.demoSQL.globalexceptionhandler.AppException;
import com.example.demoSQL.globalexceptionhandler.ErrorCode;
import com.example.demoSQL.mapper.UserMapper;
import com.example.demoSQL.repository.UserRepository;
import com.example.demoSQL.dto.UserCreationRequest;
import com.example.demoSQL.dto.UserUpdateRequest;
import com.example.demoSQL.dto.response.UserResponse;
import com.example.demoSQL.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Service
public class  UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    public User addUser( UserCreationRequest userCreationRequest) {
        if(userRepository.existsById(userCreationRequest.getId())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public UserResponse getUserById(Long Id) {
        User user = userRepository.findById(Id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .Id(user.getId())
                .build();
        return userResponse;
    }

    public User updateUser(Long Id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(Id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(userUpdateRequest.getPassword());
        return userRepository.save(user);
    }

    public void deleteUserById(Long Id) {
        userRepository.deleteById(Id);
    }

    public UserResponse findByName(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userResponse(user);
    }

    public UserResponse userResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        return userResponse;
    }

    public UserResponse getUserByName(User user) {
        User user1 = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userResponse(user1);
    }
    public UserResponse searchUser(String UserName , String Email , String firstName , String lastName) {
        if(UserName!=null&&Email!=null){
            User user = userRepository.findByUsernameAndEmail(UserName,Email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            return userResponse(user);
        }
        else if (UserName!=null){
            User user = userRepository.findByUsername(UserName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            return userResponse(user);
        }
        else if  (Email!=null){
            User user = userRepository.findByEmail(Email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            return userResponse(user);
        }
        throw new AppException(ErrorCode.USER_NOT_EXISTED);

    }
}



