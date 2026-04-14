package com.example.demoSQL.service;

import com.example.demoSQL.dto.UserCreationRequest;
import com.example.demoSQL.dto.UserUpdateRequest;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User addUser(@RequestBody UserCreationRequest userCreationRequest) {
        User newUser = new User();
        newUser.setUsername(userCreationRequest.getUsername());
        newUser.setPassword(userCreationRequest.getPassword());
        newUser.setEmail(userCreationRequest.getEmail());
        newUser.setFirstName(userCreationRequest.getFirstName());
        newUser.setLastName(userCreationRequest.getLastName());
        return userRepository.save(newUser);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long Id){
        return userRepository.findById(Id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User updateUser( Long Id , UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(Id).orElse(null);
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setPassword(userUpdateRequest.getPassword());
        return userRepository.save(user);
    }
    public void deleteUserById(Long Id){
        userRepository.deleteById(Id);
    }
}
