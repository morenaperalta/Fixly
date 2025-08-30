package com.femcoders.fixly.workorder.services;

import com.femcoders.fixly.shared.exception.EntityNotFoundException;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.services.UserAuthService;
import com.femcoders.fixly.user.services.UserServiceImpl;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import com.femcoders.fixly.workorder.WorkOrderRepository;
import com.femcoders.fixly.workorder.dtos.*;
import com.femcoders.fixly.workorder.dtos.request.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.response.*;
import com.femcoders.fixly.workorder.entities.Priority;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.SupervisionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderServiceImpl implements WorkOrderService{
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
    public WorkOrderResponseForAdmin getWorkOrderByIdForAdmin(Long id){
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, id.toString()));
        return WorkOrderMapper.workOrderResponseAdminSupToDto(workOrder, mapperService);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponseForAdmin getWorkOrderByIdForSupervisor(Long id){
        User supervisor = userAuthService.getAuthenticatedUser();
        WorkOrder workOrder = workOrderRepository.findByIdAndSupervisedBy(id, supervisor)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, id.toString()));
        return WorkOrderMapper.workOrderResponseAdminSupToDto(workOrder, mapperService);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponseForTechnician getWorkOrderByIdentifierForTechnician(String identifier){
        User technician = userAuthService.getAuthenticatedUser();
        WorkOrder workOrder = workOrderRepository.findByIdentifierAndAssignedToContaining(identifier, technician)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier));
        return WorkOrderMapper.workOrderResponseTechToDto(workOrder, mapperService);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponseForClient getWorkOrderByIdentifierForClient(String identifier){
        User client = userAuthService.getAuthenticatedUser();
        WorkOrder workOrder = workOrderRepository.findByIdentifierAndCreatedBy(identifier, client)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier));
        return WorkOrderMapper.workOrderResponseClientToDto(workOrder, mapperService);
    }
}