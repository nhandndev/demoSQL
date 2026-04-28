package com.example.demoSQL.service;
import com.example.demoSQL.enums.Role;
import com.example.demoSQL.globalexceptionhandler.AppException;
import com.example.demoSQL.globalexceptionhandler.ErrorCode;
import com.example.demoSQL.mapper.UserMapper;
import com.example.demoSQL.repository.UserRepository;
import com.example.demoSQL.dto.request.UserCreationRequest;
import com.example.demoSQL.dto.request.UserUpdateRequest;
import com.example.demoSQL.dto.response.UserResponse;
import com.example.demoSQL.entity.User;
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
import com.example.demoSQL.dto.response.UserResponse;
import java.util.HashSet;
import java.util.List;
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE , makeFinal = true)
@Service
public class  UserService {
    @Autowired
     UserRepository userRepository;
    @Autowired
     UserMapper userMapper;
    public UserResponse addUser(UserCreationRequest userCreationRequest) {

        if(userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userMapper.toUser(userCreationRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
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
    public UserResponse getUserById(Long Id) {
        User user = userRepository.findById(Id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserResponse userResponse = userMapper.toUserResponse(user);
        return userResponse;
    }

    public User updateUser(Long Id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(Id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(userUpdateRequest,user);
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
        UserResponse userResponse = userMapper.toUserResponse(user);
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
    public UserResponse getMyInfo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}



