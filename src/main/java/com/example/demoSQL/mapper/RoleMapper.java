package com.example.demoSQL.mapper;

import com.example.demoSQL.dto.request.RoleRequest;
import com.example.demoSQL.dto.response.RoleResponse;
import com.example.demoSQL.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions" , ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}
