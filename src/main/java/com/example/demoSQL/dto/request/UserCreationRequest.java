package com.example.demoSQL.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank(message = "USERNAME_INVALID")
     String username;
    @Size(min = 5, max = 100, message = "PASSWORD_INVALID")
    String password;
    @Email(message = "EMAIL_INVALID")
     String email;
     String firstName;
    String lastName;
    Long Id;
}