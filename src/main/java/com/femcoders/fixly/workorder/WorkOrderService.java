package com.femcoders.fixly.workorder;

import com.femcoders.fixly.workorder.dtos.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.CreateWorkOrderResponse;
import com.femcoders.fixly.workorder.dtos.WorkOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public CreateWorkOrderResponse createWorkOrder(CreateWorkOrderRequest request) {
        WorkOrder workOrder = WorkOrderMapper.createWorkOrderRequestToEntity(request);
        String identifier = generateIdentifier();
        workOrder.setIdentifier(identifier);

        if (workOrder.getStatus() == null) {
            workOrder.setStatus(Status.PENDING);
        }
        if (workOrder.getSupervisionStatus() == null) {
            workOrder.setSupervisionStatus(SupervisionStatus.PENDING);
        }

        WorkOrder createdWorkOrder = workOrderRepository.save(workOrder);
        return WorkOrderMapper.createWorkOrderResponseToDto(createdWorkOrder);
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