package com.example.demoSQL.dto.request;

import com.example.demoSQL.entity.Permission;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE )
public class RoleRequest {
    String name;
    String description;
    Set<String> permissions;
}
