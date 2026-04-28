package com.example.demoSQL.mapper;
import com.example.demoSQL.dto.request.UserCreationRequest;
import com.example.demoSQL.dto.request.UserUpdateRequest;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    UserResponse toUserResponse(User user);
    void updateUser (UserUpdateRequest userUpdateRequest , @MappingTarget User user);

}