package com.example.demoSQL.service;

import com.example.demoSQL.dto.request.UserCreationRequest;
import com.example.demoSQL.dto.response.UserResponse;
import com.example.demoSQL.entity.User;
import com.example.demoSQL.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;
    private User user;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2020, 1, 1);

        userCreationRequest = UserCreationRequest.builder()
                .dob(dob)
                .username("username")
                .lastName("lastName")
                .firstName("firstName")
                .password("password")
                .email("email@gmail.com")
                .build();

         user = User.builder()
                .id(10000L)
                .dob(dob)
                .username("username")
                .lastName("lastName")
                .firstName("firstName")
                .email("email@gmail.com")
                .build();
    }
    @Test
    void addUser_validRequest_success() throws Exception{
        //GIVEN
        Mockito.when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        //WHEN
        UserResponse userResponse = userService.addUser(userCreationRequest);
        //THEN
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getUsername()).isEqualTo("username");
        assertThat(userResponse.getDob()).isNotNull();
        assertThat(userResponse.getFirstName()).isEqualTo("firstName");
        assertThat(userResponse.getLastName()).isEqualTo("lastName");
        assertThat(userResponse.getDob()).isEqualTo(dob);
        assertThat(userResponse.getEmail()).isEqualTo("email@gmail.com");
    }


}
