package com.example.demoSQL.service;

import com.example.demoSQL.dto.request.PermissionRequest;
import com.example.demoSQL.dto.response.PermissionResponse;
import com.example.demoSQL.entity.Permission;
import com.example.demoSQL.mapper.PermissionMapper;
import com.example.demoSQL.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionMapper permissionMapper ;
    PermissionRepository permissionRepository;
    public PermissionResponse createPermission(PermissionRequest request) {
       Permission permission = permissionMapper.toPermission(request);
       permission = permissionRepository.save(permission);
       return permissionMapper.toPermissionResponse(permission);
    }
    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }
    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
