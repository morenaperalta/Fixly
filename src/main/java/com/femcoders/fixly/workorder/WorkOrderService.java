package com.femcoders.fixly.workorder;

import com.femcoders.fixly.shared.exception.EntityNotFoundException;
import com.femcoders.fixly.user.User;
import com.femcoders.fixly.user.UserService;
import com.femcoders.fixly.workorder.dtos.*;
import com.femcoders.fixly.workorder.enums.Priority;
import com.femcoders.fixly.workorder.enums.Status;
import com.femcoders.fixly.workorder.enums.SupervisionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderService {
    private static final String ID_FIELD = "id";
    private static final String IDENTIFIER_FIELD = "identifier";
    private final WorkOrderRepository workOrderRepository;
    private final UserService userService;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public WorkOrderResponse createWorkOrder(CreateWorkOrderRequest request) {
        WorkOrder workOrder = WorkOrderMapper.createWorkOrderRequestToEntity(request);
        String identifier = generateIdentifier();
        workOrder.setIdentifier(identifier);

        User user = userService.getAuthenticatedUser();
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
    public List<WorkOrderResponseForAdminAndSupervisor> getAllWorkOrders() {

        List<WorkOrder> workOrders = workOrderRepository.findAll();
        return workOrders.stream().map(WorkOrderMapper::workOrderResponseAdminSupToDto).toList();
    }

    @Transactional(readOnly = true)
    public List<WorkOrderResponseForTechnician> getWorkOrdersAssigned() {
        User user = userService.getAuthenticatedUser();
        List<WorkOrder> workOrders = workOrderRepository.findByAssignedToContaining(user);
        return workOrders.stream().map(WorkOrderMapper::workOrderResponseTechToDto).toList();
    }

    @Transactional(readOnly = true)
    public List<WorkOrderResponseForAdminAndSupervisor> getWorkOrdersSupervised() {
        User user = userService.getAuthenticatedUser();
        List<WorkOrder> workOrders = workOrderRepository.findBySupervisedBy(user);
        return workOrders.stream().map(WorkOrderMapper::workOrderResponseAdminSupToDto).toList();
    }

    @Transactional(readOnly = true)
    public List<WorkOrderResponseForClient> getWorkOrdersCreatedByClient() {
        User user = userService.getAuthenticatedUser();
        List<WorkOrder> workOrders = workOrderRepository.findByCreatedBy(user);
        return workOrders.stream().map(WorkOrderMapper::workOrderResponseClientToDto).toList();
    }

    @Transactional(readOnly = true)
    public WorkOrderResponseForAdminAndSupervisor getWorkOrderByIdForAdmin(Long id){
        WorkOrder workOrder = workOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, id.toString()));
        return WorkOrderMapper.workOrderResponseAdminSupToDto(workOrder);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponseForAdminAndSupervisor getWorkOrderByIdForSupervisor(Long id){
        User supervisor = userService.getAuthenticatedUser();
        WorkOrder workOrder = workOrderRepository.findByIdAndSupervisedBy(id, supervisor)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), ID_FIELD, id.toString()));
        return WorkOrderMapper.workOrderResponseAdminSupToDto(workOrder);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponseForTechnician getWorkOrderByIdentifierForTechnician(String identifier){
        User technician = userService.getAuthenticatedUser();
        WorkOrder workOrder = workOrderRepository.findByIdentifierAndAssignedToContaining(identifier, technician)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier));
        return WorkOrderMapper.workOrderResponseTechToDto(workOrder);
    }

    @Transactional(readOnly = true)
    public WorkOrderResponseForClient getWorkOrderByIdentifierForClient(String identifier){
        User client = userService.getAuthenticatedUser();
        WorkOrder workOrder = workOrderRepository.findByIdentifierAndCreatedBy(identifier, client)
                .orElseThrow(() -> new EntityNotFoundException(WorkOrder.class.getSimpleName(), IDENTIFIER_FIELD, identifier));
        return WorkOrderMapper.workOrderResponseClientToDto(workOrder);
    }

    private String generateIdentifier() {
        LocalDateTime now = LocalDateTime.now();
        String monthYear = String.format("%02d%d", now.getMonthValue(), now.getYear());

        String randomNumber = generateRandomNumber();

        return "WO-" + randomNumber + "-" + monthYear;
    }

    private String generateRandomNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            stringBuilder.append(secureRandom.nextInt(10));
        }
        return stringBuilder.toString();
    }
}