package com.femcoders.fixly.workorder;

import com.femcoders.fixly.user.User;
import com.femcoders.fixly.user.UserService;
import com.femcoders.fixly.workorder.dtos.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.WorkOrderResponse;
import com.femcoders.fixly.workorder.dtos.WorkOrderMapper;
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
        if (workOrder.getSupervisionStatus() == null) {
            workOrder.setSupervisionStatus(SupervisionStatus.PENDING);
        }

        WorkOrder createdWorkOrder = workOrderRepository.save(workOrder);
        return WorkOrderMapper.workOrderToDto(createdWorkOrder);
    }

    @Transactional(readOnly = true)
    public List<WorkOrderResponse> getAllWorkOrders(){
        List<WorkOrder> workOrders = workOrderRepository.findAll();
        return workOrders.stream().map(WorkOrderMapper::workOrderToDto).toList();
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