package com.femcoders.fixly.workorder.dtos;

import com.femcoders.fixly.user.services.UserAuthService;
import com.femcoders.fixly.workorder.dtos.response.*;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import com.femcoders.fixly.workorder.dtos.request.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.services.implementations.WorkOrderMapperServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public final class WorkOrderMapper {
    private final WorkOrderMapperServiceImpl mapperService;
    private final UserAuthService userAuthService;

    public static WorkOrder createWorkOrderRequestToEntity(CreateWorkOrderRequest request) {
        return WorkOrder.builder().title(request.title().trim()).description(request.description().trim()).location(request.location().trim()).build();
    }

    public static WorkOrderSummaryResponse workOrderToDto(WorkOrder workorder) {
        return new WorkOrderSummaryResponse(workorder.getIdentifier(), workorder.getTitle(), workorder.getDescription(), workorder.getLocation(), workorder.getCreatedAt());
    }

    public WorkOrderResponseForAdmin createAdminResponse(WorkOrder workOrder) {
        return new WorkOrderResponseForAdmin(workOrder.getId(), workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getPriority(),workOrder.getStatus(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), mapperService.mapCreatedBy(workOrder), mapperService.mapSupervisedBy(workOrder),mapperService.mapAssignedTo(workOrder), mapperService.mapComment(workOrder), mapperService.mapAttachment(workOrder));
    }

    public WorkOrderResponseForTechnician createWorkOrderResponseForTechnician(WorkOrder workOrder) {
        return new WorkOrderResponseForTechnician(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getPriority(),workOrder.getStatus(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), mapperService.mapCreatedBy(workOrder), mapperService.mapAssignedTo(workOrder), mapperService.mapComment(workOrder), mapperService.mapAttachment(workOrder));
    }

    public WorkOrderResponseForClient createWorkOrderResponseForClient(WorkOrder workOrder) {
        return new WorkOrderResponseForClient(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(),workOrder.getCreatedAt(), workOrder.getUpdatedAt(),mapperService.mapComment(workOrder), mapperService.mapAttachment(workOrder));
    }

    public WorkOrderResponse createWorkOrderResponseByRole(WorkOrder workOrder, Authentication auth) {
        String role = userAuthService.extractRole(auth);

        return switch (role) {
            case "ROLE_ADMIN", "ROLE_SUPERVISOR" -> createAdminResponse(workOrder);
            case "ROLE_TECHNICIAN" -> createWorkOrderResponseForTechnician(workOrder);
            case "ROLE_CLIENT" -> createWorkOrderResponseForClient(workOrder);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

    public List<WorkOrderResponse> createResponseList(List<WorkOrder> workOrders, Authentication auth) {
        return workOrders.stream().map(workOrder -> createWorkOrderResponseByRole(workOrder, auth)).toList();
    }
}