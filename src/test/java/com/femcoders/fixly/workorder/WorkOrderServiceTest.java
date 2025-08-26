package com.femcoders.fixly.workorder;

import com.femcoders.fixly.user.Role;
import com.femcoders.fixly.user.User;
import com.femcoders.fixly.user.UserService;
import com.femcoders.fixly.workorder.dtos.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.WorkOrderResponse;
import com.femcoders.fixly.workorder.dtos.WorkOrderResponseForAdminAndSupervisor;
import com.femcoders.fixly.workorder.enums.Status;
import com.femcoders.fixly.workorder.enums.SupervisionStatus;
import com.femcoders.fixly.workorder.services.WorkOrderIdentifierService;
import com.femcoders.fixly.workorder.services.WorkOrderMapperService;
import com.femcoders.fixly.workorder.services.WorkOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for WorkOrderService")
class WorkOrderServiceTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @Mock
    private UserService userService;

    @Mock
    private WorkOrderIdentifierService identifierService;

    @Mock
    private WorkOrderMapperService mapperService;

    @InjectMocks
    private WorkOrderService workOrderService;

    private CreateWorkOrderRequest createWorkOrderRequest;
    private User authenticatedUser;
    private WorkOrder workOrder1;
    private WorkOrder workOrder2;
    private User technician;
    private User technician2;

    @BeforeEach
    void setUp() {
        createWorkOrderRequest = new CreateWorkOrderRequest("Fix air conditioning system", "The AC unit is not working properly in the main office", "Main Office Building A");

        authenticatedUser = new User(1L, "client1", "client1@email.com", "password123", "John", "Doe", "Company Inc", Role.CLIENT, LocalDateTime.now(), LocalDateTime.now());

        technician = new User(2L, "tech1", "tech1@email.com", "password123", "Jane", "Smith", "Fixly Corp", Role.TECHNICIAN, LocalDateTime.now(), LocalDateTime.now());

        technician2 = new User(3L, "tech2", "tech2@email.com", "password123", "Jane", "Smith", "Fixly Corp", Role.TECHNICIAN, LocalDateTime.now(), LocalDateTime.now());

        workOrder1 = WorkOrder.builder().id(1L).identifier("WO-123456-082025").title("Fix air conditioning system").description("The AC unit is not working properly in the main office").location("Main Office Building A").status(Status.PENDING).supervisionStatus(SupervisionStatus.PENDING).createdBy(authenticatedUser).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).assignedTo(List.of()).build();

        workOrder2 = WorkOrder.builder().id(2L).identifier("WO-234567-082025").title("Replace broken window").description("Window in conference room needs replacement").location("Conference Room 201").status(Status.ASSIGNED).supervisionStatus(SupervisionStatus.PENDING).createdBy(authenticatedUser).createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).assignedTo(List.of(technician)).build();
    }

    @Nested
    @DisplayName("Create work order")
    class CreateWorkOrderTests {
        @Test
        @DisplayName("When request is valid, it should create work order successfully")
        void createWorkOrder_whenValidRequest_returnWorkOrderResponse() {
            when(userService.getAuthenticatedUser()).thenReturn(authenticatedUser);
            when(workOrderRepository.save(any(WorkOrder.class))).thenReturn(workOrder1);

            WorkOrderResponse result = workOrderService.createWorkOrder(createWorkOrderRequest);

            assertNotNull(result);
            assertEquals(workOrder1.getIdentifier(), result.identifier());
            assertEquals(workOrder1.getTitle(), result.title());
            assertEquals(workOrder1.getDescription(), result.description());
            assertEquals(workOrder1.getLocation(), result.location());
            assertEquals(workOrder1.getCreatedAt(), result.createdAt());

            verify(userService, times(1)).getAuthenticatedUser();
            verify(workOrderRepository, times(1)).save(any(WorkOrder.class));
        }

        @Test
        @DisplayName("When creating work order, it should set default status and supervision status")
        void createWorkOrder_whenCreated_shouldSetDefaultStatuses() {
            when(userService.getAuthenticatedUser()).thenReturn(authenticatedUser);

            String generatedIdentifier = "WO-123456";
            when(identifierService.generateIdentifier()).thenReturn(generatedIdentifier);

            when(workOrderRepository.save(any(WorkOrder.class))).thenAnswer(invocation -> {
                WorkOrder savedWorkOrder = invocation.getArgument(0);

                assertEquals(Status.PENDING, savedWorkOrder.getStatus());
                assertEquals(SupervisionStatus.PENDING, savedWorkOrder.getSupervisionStatus());
                assertEquals(authenticatedUser, savedWorkOrder.getCreatedBy());
                assertNotNull(savedWorkOrder.getIdentifier());
                assertEquals(generatedIdentifier, savedWorkOrder.getIdentifier());

                return workOrder1;
            });

            WorkOrderResponse result = workOrderService.createWorkOrder(createWorkOrderRequest);

            assertNotNull(result);
            verify(userService, times(1)).getAuthenticatedUser();
            verify(identifierService, times(1)).generateIdentifier();
            verify(workOrderRepository, times(1)).save(any(WorkOrder.class));
        }
    }

    @Nested
    @DisplayName("Get all work orders")
    class GetAllWorkOrdersTests {
        @Test
        @DisplayName("When there is at least one work order, it should return a list of WorkOrderResponse")
        void getAllWorkOrders_whenExistsWorkOrders_returnListOfWorkOrderResponse() {
            when(workOrderRepository.findAll()).thenReturn(List.of(workOrder1, workOrder2));

            List<WorkOrderResponseForAdminAndSupervisor> result = workOrderService.getAllWorkOrders();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals(workOrder1.getIdentifier(), result.get(0).identifier());
            assertEquals(workOrder1.getTitle(), result.get(0).title());
            assertEquals(workOrder2.getIdentifier(), result.get(1).identifier());
            assertEquals(workOrder2.getTitle(), result.get(1).title());

            verify(workOrderRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("When there are no work orders, it should return empty list")
        void getAllWorkOrders_whenWorkOrdersNotExist_returnEmptyList() {
            when(workOrderRepository.findAll()).thenReturn(Collections.emptyList());

            List<WorkOrderResponseForAdminAndSupervisor> result = workOrderService.getAllWorkOrders();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            assertEquals(0, result.size());

            verify(workOrderRepository, times(1)).findAll();
        }
    }
}