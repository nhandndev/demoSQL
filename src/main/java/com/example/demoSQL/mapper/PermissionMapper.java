package com.example.demoSQL.mapper;

import com.example.demoSQL.dto.request.PermissionRequest;
import com.example.demoSQL.dto.response.PermissionResponse;
import com.example.demoSQL.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionResponse toPermissionResponse(Permission permission);

    Permission toPermission(PermissionRequest permissionRequest);
}
