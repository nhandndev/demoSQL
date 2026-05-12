package com.example.demoSQL.dto.response;

import com.example.demoSQL.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String username;
    String email;
    String firstName;
    String lastName;
    LocalDate dob;
    Set<RoleResponse> roles;
}
