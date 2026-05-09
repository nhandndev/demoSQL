package com.example.demoSQL.service;

import com.example.demoSQL.dto.request.RoleRequest;
import com.example.demoSQL.dto.response.PermissionResponse;
import com.example.demoSQL.dto.response.RoleResponse;
import com.example.demoSQL.entity.Role;
import com.example.demoSQL.mapper.RoleMapper;
import com.example.demoSQL.repository.PermissionRepository;
import com.example.demoSQL.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;
    public RoleResponse create(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }
    public List<RoleResponse> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> roleMapper.toRoleResponse(role)).toList();
    }
    public void delete(String role){
        roleRepository.deleteById(role);

    }
}
