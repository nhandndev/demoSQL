package com.example.demoSQL.mapper;
import com.example.demoSQL.dto.UserCreationRequest;
import com.example.demoSQL.entity.User;
import org.mapstruct.Mapper;
@Mapper(componentModel = "srping")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
}