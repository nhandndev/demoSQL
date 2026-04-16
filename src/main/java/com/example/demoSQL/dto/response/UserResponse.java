package com.example.demoSQL.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private Long Id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
