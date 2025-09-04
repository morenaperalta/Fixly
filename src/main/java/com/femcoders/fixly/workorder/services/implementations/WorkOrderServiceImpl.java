package com.femcoders.fixly.workorder.services.implementations;

import com.femcoders.fixly.shared.exception.EntityNotFoundException;
import com.femcoders.fixly.user.UserRepository;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.services.UserAuthService;
import com.femcoders.fixly.workorder.WorkOrderRepository;
import com.femcoders.fixly.workorder.dtos.WorkOrderMapper;
import com.femcoders.fixly.workorder.dtos.request.*;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderResponse;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderSummaryResponse;
import com.femcoders.fixly.workorder.entities.Priority;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.SupervisionStatus;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import com.femcoders.fixly.workorder.services.WorkOrderIdentifierService;
import com.femcoders.fixly.workorder.services.WorkOrderService;
import com.femcoders.fixly.workorder.services.WorkOrderValidationService;
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
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_SUPERVISOR = "ROLE_SUPERVISOR";
    private final WorkOrderRepository workOrderRepository;
    private final UserRepository userRepository;
    private final WorkOrderIdentifierService identifierService;
    private final WorkOrderValidationService workOrderValidationService;
    private final UserAuthService userAuthService;
    private final WorkOrderMapper workOrderMapper;

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
        return workOrderMapper.createResponseList(workOrders, auth);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponse getWorkOrderByIdentifier(String identifier, Authentication auth) {
        String role = userAuthService.extractRole(auth);
        User user = userAuthService.getAuthenticatedUser();
        WorkOrder workOrder;
        switch (role) {
            case "ROLE_ADMIN" ->
                    workOrder = workOrderRepository.findByIdentifier(identifier).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, identifier));
            case "ROLE_SUPERVISOR" ->
                    workOrder = workOrderRepository.findByIdentifierAndSupervisedBy(identifier, user).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, identifier, "supervised"));
            case "ROLE_TECHNICIAN" ->
                    workOrder = workOrderRepository.findByIdentifierAndAssignedToContaining(identifier, user).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier, "assigned"));
            case "ROLE_CLIENT" ->
                    workOrder = workOrderRepository.findByIdentifierAndCreatedBy(identifier, user).orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier, "created"));
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        }

        return workOrderMapper.createWorkOrderResponseByRole(workOrder, auth);
    }

    @Transactional
    public WorkOrderResponse updateWorkOrder(String identifier, UpdateWorkOrderRequest request, Authentication auth) {
        String role = userAuthService.extractRole(auth);
        User currentUser = userAuthService.getAuthenticatedUser();
        WorkOrder workOrder = workOrderRepository.findByIdentifier(identifier)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier));

        workOrderValidationService.validateAccessPermissions(workOrder, currentUser, role);

        boolean hasChanges = false;

        if (request instanceof UpdateWorkOrderRequestForAdmin adminReq) {
            hasChanges |= updatePriority(workOrder, adminReq.priority(), role);
            hasChanges |= updateStatus(workOrder, adminReq.status(), role, currentUser, workOrder);
            hasChanges |= updateSupervisionStatus(workOrder, adminReq.supervisionStatus(), role);
            hasChanges |= updateTechnicians(workOrder, adminReq.technicianIds(), role);
            hasChanges |= updateSupervisor(workOrder, adminReq.supervisorId(), role);
        } else if (request instanceof UpdateWorkOrderRequestForSupervisor supervisorReq) {
            hasChanges |= updatePriority(workOrder, supervisorReq.priority(), role);
            hasChanges |= updateStatus(workOrder, supervisorReq.status(), role, currentUser, workOrder);
            hasChanges |= updateSupervisionStatus(workOrder, supervisorReq.supervisionStatus(), role);
            hasChanges |= updateTechnicians(workOrder, supervisorReq.technicianIds(), role);
        } else if (request instanceof UpdateWorkOrderRequestForTechnician technicianReq) {
            hasChanges |= updateStatus(workOrder, technicianReq.status(), role, currentUser, workOrder);
        }

        if (hasChanges) {
            WorkOrder updatedWorkOrder = workOrderRepository.save(workOrder);
            return workOrderMapper.createWorkOrderResponseByRole(updatedWorkOrder, auth);
        }

        return workOrderMapper.createWorkOrderResponseByRole(workOrder, auth);
    }

    private boolean updatePriority(WorkOrder workOrder, Priority newPriority, String role) {
        if (newPriority == null || workOrder.getPriority() == newPriority) {
            return false;
        }

        workOrderValidationService.validatePermissionForField("priority", role, ROLE_ADMIN, ROLE_SUPERVISOR);
        workOrder.setPriority(newPriority);
        return true;
    }

    private boolean updateStatus(WorkOrder workOrder, Status newStatus, String role, User currentUser, WorkOrder workOrderForValidation) {
        if (newStatus == null || workOrder.getStatus() == newStatus) {
            return false;
        }

        Status currentStatus = workOrder.getStatus();
        workOrderValidationService.validateStatusTransition(currentStatus, newStatus, role, workOrderForValidation, currentUser);
        workOrder.setStatus(newStatus);
        return true;
    }

    private boolean updateSupervisionStatus(WorkOrder workOrder, SupervisionStatus newSupervisionStatus, String role) {
        if (newSupervisionStatus == null || workOrder.getSupervisionStatus() == newSupervisionStatus) {
            return false;
        }

        workOrderValidationService.validatePermissionForField("supervisionStatus", role, ROLE_ADMIN, ROLE_SUPERVISOR);
        workOrder.setSupervisionStatus(newSupervisionStatus);
        return true;
    }

    private boolean updateTechnicians(WorkOrder workOrder, List<Long> technicianIds, String role) {
        if (technicianIds == null || technicianIds.isEmpty()) {
            return false;
        }

        workOrderValidationService.validatePermissionForField("technicians", role, ROLE_ADMIN, ROLE_SUPERVISOR);

        List<User> technicians = userRepository.findAllById(technicianIds);
        if (technicians.size() != technicianIds.size()) {
            throw new EntityNotFoundException("User", "id", "one or more technician IDs");
        }

        technicians.forEach(workOrderValidationService::validateTechnicianRole);

        workOrder.setAssignedTo(technicians);
        return true;
    }

    private boolean updateSupervisor(WorkOrder workOrder, Long supervisorId, String role) {
        if (supervisorId == null) {
            return false;
        }

        workOrderValidationService.validatePermissionForField("supervisor", role, ROLE_ADMIN);

        User supervisor = userRepository.findById(supervisorId)
                .orElseThrow(() -> new EntityNotFoundException("User", "id", supervisorId.toString()));

        workOrderValidationService.validateSupervisorRole(supervisor);

        if (workOrder.getSupervisedBy() != null && workOrder.getSupervisedBy().equals(supervisor)) {
            return false;
        }

        workOrder.setSupervisedBy(supervisor);
        return true;
    }

    @Transactional
    public void deleteWorkOrder(Long id){
        if (!workOrderRepository.existsById(id)) {
            throw new EntityNotFoundException(WorkOrder.class.getSimpleName(), "id", id.toString());
        }
        workOrderRepository.deleteById(id);
    }
}