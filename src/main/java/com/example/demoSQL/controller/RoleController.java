package com.example.demoSQL.controller;

import com.example.demoSQL.dto.ApiResponse;
import com.example.demoSQL.dto.request.RoleRequest;
import com.example.demoSQL.dto.response.PermissionResponse;
import com.example.demoSQL.dto.response.RoleResponse;
import com.example.demoSQL.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(RoleRequest roleRequest) {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }
    @DeleteMapping
    ApiResponse<Void> delete(RoleRequest roleRequest) {
        return ApiResponse.<Void>builder()
                .build();
    }
}
