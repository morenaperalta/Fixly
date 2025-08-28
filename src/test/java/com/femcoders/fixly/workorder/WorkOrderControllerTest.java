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

        @Test
        @DisplayName("Should return 403 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getAllWorkOrders_whenAuthenticatedAsClient_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as TECHNICIAN")
        @WithMockUser(roles = {"TECHNICIAN"})
        void getAllWorkOrders_whenAuthenticatedAsTechnician_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as SUPERVISOR")
        @WithMockUser(roles = {"SUPERVISOR"})
        void getAllWorkOrders_whenAuthenticatedAsSupervisor_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get work orders assigned to technician")
    class GetWorkOrdersAssigned {

        @Test
        @DisplayName("Should return 200 when authenticated as TECHNICIAN")
        @WithMockUser(roles = {"TECHNICIAN"})
        void getWorkOrdersAssigned_whenAuthenticatedAsTechnician_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/assigned")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getWorkOrdersAssigned_whenAuthenticatedAsClient_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/assigned")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as ADMIN")
        @WithMockUser(roles = {"ADMIN"})
        void getWorkOrdersAssigned_whenAuthenticatedAsAdmin_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/assigned")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get work orders supervised")
    class GetWorkOrdersSupervised {

        @Test
        @DisplayName("Should return 200 when authenticated as SUPERVISOR")
        @WithMockUser(roles = {"SUPERVISOR"})
        void getWorkOrdersSupervised_whenAuthenticatedAsSupervisor_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/supervised")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getWorkOrdersSupervised_whenAuthenticatedAsClient_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/supervised")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as ADMIN")
        @WithMockUser(roles = {"ADMIN"})
        void getWorkOrdersSupervised_whenAuthenticatedAsAdmin_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/supervised")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get work orders created by client")
    class GetWorkOrdersCreatedByClient {

        @Test
        @DisplayName("Should return 200 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getWorkOrdersCreatedByClient_whenAuthenticatedAsClient_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/created")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as TECHNICIAN")
        @WithMockUser(roles = {"TECHNICIAN"})
        void getWorkOrdersCreatedByClient_whenAuthenticatedAsTechnician_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/created")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as ADMIN")
        @WithMockUser(roles = {"ADMIN"})
        void getWorkOrdersCreatedByClient_whenAuthenticatedAsAdmin_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/created")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get work order by ID for admin")
    class GetWorkOrderByIdForAdmin {

        @Test
        @DisplayName("Should return 200 when authenticated as ADMIN")
        @WithMockUser(roles = {"ADMIN"})
        void getWorkOrderById_whenAuthenticatedAsAdmin_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1));
        }

        @Test
        @DisplayName("Should return 403 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getWorkOrderById_whenAuthenticatedAsClient_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Should return 404 when work order does not exist")
        @WithMockUser(roles = {"ADMIN"})
        void getWorkOrderById_whenWorkOrderDoesNotExist_ReturnNotFound() throws Exception {
            mockMvc.perform(get("/api/workorders/0")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Get work order by ID for supervisor")
    class GetWorkOrderByIdForSupervisor {

        @Test
        @DisplayName("Should return 200 when authenticated as SUPERVISOR")
        @WithMockUser(roles = {"SUPERVISOR"})
        void getWorkOrderByIdForSupervisor_whenAuthenticatedAsSupervisor_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/supervised/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getWorkOrderByIdForSupervisor_whenAuthenticatedAsClient_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/supervised/1")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get work order by identifier for technician")
    class GetWorkOrderByIdentifierForTechnician {

        @Test
        @DisplayName("Should return 200 when authenticated as TECHNICIAN")
        @WithMockUser(roles = {"TECHNICIAN"})
        void getWorkOrderByIdentifierForTechnician_whenAuthenticatedAsTechnician_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/assigned/WO-456789-082025").
                    accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getWorkOrderByIdentifierForTechnician_whenAuthenticatedAsClient_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/assigned/WO-456789-082025")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("Get work order by identifier for client")
    class GetWorkOrderByIdentifierForClient {

        @Test
        @DisplayName("Should return 200 when authenticated as CLIENT")
        @WithMockUser(roles = {"CLIENT"})
        void getWorkOrderByIdentifierForClient_whenAuthenticatedAsClient_ReturnOk() throws Exception {
            mockMvc.perform(get("/api/workorders/created/WO-001")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 403 when authenticated as TECHNICIAN")
        @WithMockUser(roles = {"TECHNICIAN"})
        void getWorkOrderByIdentifierForClient_whenAuthenticatedAsTechnician_ReturnForbidden() throws Exception {
            mockMvc.perform(get("/api/workorders/created/WO-001")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden());
        }
    }
}
