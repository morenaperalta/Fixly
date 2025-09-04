package com.femcoders.fixly.workorder;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayName("WorkOrderController Integration Tests")
class WorkOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("Get all work orders")
    class GetAllWorkOrders {

        @Test
        @DisplayName("Should return 200 when authenticated as ADMIN")
        @WithMockUser(roles = {"ADMIN"})
        void getAllWorkOrders_whenAuthenticatedAsAdmin_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
        }
    }
    @Nested
    @DisplayName("Get work order by identifier")
    class GetWorkOrderByIdentifier {

        @Test
        @DisplayName("Should return 200 when authenticated")
        @WithMockUser(roles = {"TECHNICIAN"})
        void getWorkOrderByIdentifierForTechnician_whenAuthenticatedAsTechnician_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/assigned/WO-456789-082025").
                    accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }
}
