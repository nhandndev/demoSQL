package com.example.demoSQL.dto.request;

import com.example.demoSQL.validator.DobConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    @DobConstraint(min = 18)
    LocalDate dob;
    List<String> roles;
}
