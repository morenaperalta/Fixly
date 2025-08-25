package com.femcoders.fixly.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.femcoders.fixly.auth.dtos.LoginRequest;
import com.femcoders.fixly.auth.dtos.RegistrationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("AuthController Integration Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Register")
    class RegisterTests {
        @Test
        @DisplayName("Should return 201 when request is valid")
        void register_WhenValidRequest_ReturnIsCreated() throws Exception {
            RegistrationRequest registrationRequest = new RegistrationRequest(
                    "newuser",
                    "newuser@email.com",
                    "Password123##",
                    "New",
                    "User",
                    "Test Company"
            );

            mockMvc.perform(post("/api/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registrationRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.username").value(registrationRequest.username()));
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

        @Test
        @DisplayName("Should return 409 when registration username already exists")
        void register_WhenUsernameAlreadyExists_ReturnsConflict() throws Exception {
            RegistrationRequest registrationRequest = new RegistrationRequest(
                    "client",
                    "newuser@email.com",
                    "Password123##",
                    "New",
                    "User",
                    "Test Company"
            );

            mockMvc.perform(post("/api/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registrationRequest)))
                    .andExpect(status().isConflict());
        }

        @Test
        @DisplayName("Should return 409 when registration email already exists")
        void register_WhenEmailAlreadyExists_ReturnsConflict() throws Exception {
            RegistrationRequest registrationRequest = new RegistrationRequest(
                    "newuser",
                    "admin@fixly.com",
                    "Password123##",
                    "New",
                    "User",
                    "Test Company"
            );

            mockMvc.perform(post("/api/auth/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(registrationRequest)))
                    .andExpect(status().isConflict());
        }
    }

    @Nested
    @DisplayName("Login")
    class LoginTests {
        @Test
        @DisplayName("Should return JWT token when login is successful")
        void login_WhenValidCredentials_ReturnsJwtResponse() throws Exception {
            LoginRequest loginRequest = new LoginRequest("admin", "Admin123##");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.token").exists());
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
            LoginRequest invalidCredentials = new LoginRequest("admin", "WrongPassword");

            mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidCredentials)))
                    .andExpect(status().isBadRequest());
        }
    }
}