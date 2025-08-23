package com.femcoders.fixly.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.fixly.user.dtos.UserResponseForAdmin;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("UserController Integration Tests")
class UserControllerTest {

    @TestConfiguration
    static class TestConfig{
        @Bean
        @Primary
        public UserService userService(){
            return mock(UserService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserResponseForAdmin userResponse1;
    private UserResponseForAdmin userResponse2;
    private List<UserResponseForAdmin> userList;

    @BeforeEach
    void setUp() {
        userResponse1 = new UserResponseForAdmin(
                1L,
                "User1",
                "user1@email.com",
                "FirstName",
                "LastName",
                "Company",
                Role.CLIENT,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        userResponse2 = new UserResponseForAdmin(
                2L,
                "User2",
                "user2@email.com",
                "SecondName",
                "SecondLast",
                "Company2",
                Role.ADMIN,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        userList = List.of(userResponse1, userResponse2);
    }

    @Nested
    @DisplayName("Get all users")
    class GetAllUsers {

        @Test
        @DisplayName("Should return 200 when authenticated as ADMIN or SUPERVISOR")
        @WithMockUser(roles = {"ADMIN", "SUPERVISOR"})
        void getAllUsers_whenAuthenticatedAsAdminOrSupervisor_shouldReturnOk() throws Exception {

            when(userService.getAllUsers()).thenReturn(userList);

            mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$", Matchers.hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].username").value("User1"))
                    .andExpect(jsonPath("$[0].email").value("user1@email.com"))
                    .andExpect(jsonPath("$[0].firstName").value("FirstName"))
                    .andExpect(jsonPath("$[0].lastName").value("LastName"))
                    .andExpect(jsonPath("$[0].company").value("Company"))
                    .andExpect(jsonPath("$[0].role").value("CLIENT"))
                    .andExpect(jsonPath("$[1].id").value(2L))
                    .andExpect(jsonPath("$[1].username").value("User2"))
                    .andExpect(jsonPath("$[1].email").value("user2@email.com"))
                    .andExpect(jsonPath("$[1].firstName").value("SecondName"))
                    .andExpect(jsonPath("$[1].lastName").value("SecondLast"))
                    .andExpect(jsonPath("$[1].company").value("Company2"))
                    .andExpect(jsonPath("$[1].role").value("ADMIN"));

            verify(userService, times(1)).getAllUsers();
        }
    }
}