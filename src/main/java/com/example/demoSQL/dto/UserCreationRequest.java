package com.example.demoSQL.dto;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.globalexceptionhandler.ErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreationRequest {
    @NotBlank(message = "USERNAME_INVALID")
    private String username;
    @NotBlank
    @Size(min = 5, max = 100,message = "PASSWORD_INVALID")
    private String password;
    @Email(message = "EMAIL_INVALID")
    private String email;
    private String firstName;
    private String lastName;
    private Long Id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
