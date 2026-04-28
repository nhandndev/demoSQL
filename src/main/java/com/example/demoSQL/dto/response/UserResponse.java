package com.example.demoSQL.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private Long Id;
    private String username;
    String email;
    String firstName;
    String lastName;
    Set<String> roles;
}
