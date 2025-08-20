package com.femcoders.fixly.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.fixly.auth.dtos.JwtResponse;
import com.femcoders.fixly.auth.dtos.LoginRequest;
import com.femcoders.fixly.auth.dtos.RegistrationRequest;
import com.femcoders.fixly.user.dtos.UserResponse;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Tests for AuthController")
class AuthControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public AuthService authService() {
            return mock(AuthService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private RegistrationRequest registrationRequest;
    private UserResponse userResponse;
    private LoginRequest loginRequest;
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        registrationRequest = new RegistrationRequest("User1", "user1@email.com", "Password123!", "FirstName", "LastName", "Company");
        userResponse = new UserResponse ("User1", "user1@email.com", "FirstName", "LastName", "Company");
        loginRequest = new LoginRequest("User1", "Password123!");
        jwtResponse = new JwtResponse("token");
    }

    @Nested
    @DisplayName("Register")
    class RegisterTests {
        @Test
        @DisplayName("Should register user correctly when request is valid")
        void register_WhenValidRequest_ReturnsCreatedUser() throws Exception {
            when(authService.register(any(RegistrationRequest.class))).thenReturn(userResponse);

            mockMvc.perform(post("/api/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registrationRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.username").value("User1"))
                    .andExpect(jsonPath("$.email").value("user1@email.com"));
        }

        @Test
        @DisplayName("Should return 400 when registration request is invalid")
        void register_WhenInvalidRequest_ReturnsBadRequest() throws Exception {
            RegistrationRequest invalidRequest = new RegistrationRequest("", "", "", "", "", "");

            mockMvc.perform(post("/api/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Login")
    class LoginTests {
        @Test
        @DisplayName("Should return JWT token when login is successful")
        void login_WhenValidCredentials_ReturnsJwtResponse() throws Exception {
            when(authService.login(any(LoginRequest.class))).thenReturn(jwtResponse);

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").value("token"));
        }

        @Test
        @DisplayName("Should return 400 when login request is invalid")
        void login_WhenInvalidRequest_ReturnsBadRequest() throws Exception {
            LoginRequest invalidLogin = new LoginRequest("", "");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidLogin)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 401 when credentials are invalid")
        void login_WhenInvalidCredentials_ReturnsUnauthorized() throws Exception {
            when(authService.login(any(LoginRequest.class))).thenThrow(new BadCredentialsException("Invalid credentials"));

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isUnauthorized());
        }
    }
}