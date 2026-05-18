package com.example.demoSQL.controller;

import com.example.demoSQL.dto.request.UserCreationRequest;
import com.example.demoSQL.dto.response.UserResponse;
import com.example.demoSQL.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource("/test.properties")
public class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2000, 1, 1);

        userCreationRequest = UserCreationRequest.builder()
                .dob(dob)
                .username("username")
                .lastName("lastName")
                .firstName("firstName")
                .password("password")
                .email("email@gmail.com")
                .build();

        userResponse = UserResponse.builder()
                .id(10000L)
                .dob(dob)
                .username("username")
                .lastName("lastName")
                .firstName("firstName")
                .email("email@gmail.com")
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        String content = objectMapper.writeValueAsString(userCreationRequest);

        Mockito.when(userService.addUser(Mockito.any(UserCreationRequest.class)))
                .thenReturn(userResponse);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.result.username").value("username"))
                .andExpect(jsonPath("$.result.firstName").value("firstName"))
                .andExpect(jsonPath("$.result.lastName").value("lastName"))
                .andExpect(jsonPath("$.result.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.result.dob").value(dob.toString()));
    }
}