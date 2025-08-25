package com.femcoders.fixly.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("UserController Integration Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Get all users")
    class GetAllUsers {

        @Test
        @DisplayName("Should return 200 when authenticated as ADMIN or SUPERVISOR")
        @WithMockUser(roles = {"ADMIN"})
        void getAllUsers_whenAuthenticatedAsAdmin_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", Matchers.hasSize(10)))
                    .andExpect(jsonPath("$[0].username").value("admin"))
                    .andExpect(jsonPath("$[0].email").value("admin@fixly.com"));
        }

        @Test
        @DisplayName("Should return 403 when authenticated as CLIENT or TECHNICIAN")
        @WithMockUser(roles = {"CLIENT"})
        void getAllUsers_whenAuthenticatedAsClient_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/users").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }
}