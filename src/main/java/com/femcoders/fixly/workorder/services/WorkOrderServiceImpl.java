package com.femcoders.fixly.workorder.services;

import com.femcoders.fixly.shared.exception.EntityNotFoundException;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.services.UserAuthService;
import com.femcoders.fixly.user.services.UserServiceImpl;
import com.femcoders.fixly.workorder.WorkOrderRepository;
import com.femcoders.fixly.workorder.dtos.WorkOrderMapper;
import com.femcoders.fixly.workorder.dtos.request.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.response.*;
import com.femcoders.fixly.workorder.entities.Priority;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.SupervisionStatus;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderServiceImpl implements WorkOrderService {
    private static final String ID_FIELD = "id";
    private static final String IDENTIFIER_FIELD = "identifier";
    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderIdentifierServiceImpl identifierService;
    private final UserAuthService userAuthService;
    private final UserServiceImpl userService;
    private final WorkOrderMapperServiceImpl mapperService;
    private final WorkOrderResponseFactory workOrderResponseFactory;

    @Transactional
    public WorkOrderSummaryResponse createWorkOrder(CreateWorkOrderRequest request) {
        WorkOrder workOrder = WorkOrderMapper.createWorkOrderRequestToEntity(request);
        String identifier = identifierService.generateIdentifier();
        workOrder.setIdentifier(identifier);

        User user = userAuthService.getAuthenticatedUser();
        workOrder.setCreatedBy(user);

        if (workOrder.getStatus() == null) {
            workOrder.setStatus(Status.PENDING);
        }
        if (workOrder.getPriority() == null) {
            workOrder.setPriority(Priority.PENDING);
        }
        if (workOrder.getSupervisionStatus() == null) {
            workOrder.setSupervisionStatus(SupervisionStatus.PENDING);
        }

        WorkOrder createdWorkOrder = workOrderRepository.save(workOrder);
        return WorkOrderMapper.workOrderToDto(createdWorkOrder);
    }

    @Transactional(readOnly = true)
    public List<WorkOrderResponse> getAllWorkOrders(Authentication auth) {
        String role = userAuthService.extractRole(auth);
        User user = userAuthService.getAuthenticatedUser();
        List<WorkOrder> workOrders;
        switch (role) {
            case "ROLE_ADMIN" -> workOrders = workOrderRepository.findAll();
            case "ROLE_SUPERVISOR" -> workOrders = workOrderRepository.findBySupervisedBy(user);
            case "ROLE_TECHNICIAN" -> workOrders = workOrderRepository.findByAssignedToContaining(user);
            case "ROLE_CLIENT" -> workOrders = workOrderRepository.findByCreatedBy(user);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        }
        return workOrderResponseFactory.createResponseList(workOrders, auth);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponse getWorkOrderByIdentifier(String identifier, Authentication auth) {
        String role = userAuthService.extractRole(auth);
        User user = userAuthService.getAuthenticatedUser();
        WorkOrder workOrder;
        switch (role) {
            case "ROLE_ADMIN" -> workOrder = workOrderRepository.findByIdentifier(identifier).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, identifier));
            case "ROLE_SUPERVISOR" -> workOrder = workOrderRepository.findByIdentifierAndSupervisedBy(identifier, user).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, identifier, "supervised"));
            case "ROLE_TECHNICIAN" ->
                    workOrder = workOrderRepository.findByIdentifierAndAssignedToContaining(identifier, user).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier, "assigned"));
            case "ROLE_CLIENT" -> workOrder = workOrderRepository.findByIdentifierAndCreatedBy(identifier, user).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier, "created"));
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        }

        return workOrderResponseFactory.createWorkOrderResponseByRole(workOrder, auth);
    }
}