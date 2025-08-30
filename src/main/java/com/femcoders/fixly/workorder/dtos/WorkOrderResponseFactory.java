package com.femcoders.fixly.workorder.dtos;

import com.femcoders.fixly.workorder.WorkOrder;
import com.femcoders.fixly.workorder.services.WorkOrderMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorkOrderResponseFactory {
    private final WorkOrderMapperService mapperService;

    public WorkOrderResponse createWorkOrderResponseByRole(WorkOrder workOrder, Authentication auth) {
        String role = extractRole(auth);

        return switch (role) {
            case "ROLE_ADMIN", "ROLE_SUPERVISOR" -> createAdminResponse(workOrder);
            case "ROLE_TECHNICIAN" -> createWorkOrderResponseForTechnician(workOrder);
            case "ROLE_CLIENT" -> createWorkOrderResponseForClient(workOrder);
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        };
    }

    private String extractRole(Authentication auth) {
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("NO_ROLE");
    }

    public WorkOrderResponseForAdmin createAdminResponse(WorkOrder workOrder) {
        return new WorkOrderResponseForAdmin(workOrder.getId(), workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getPriority(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), mapperService.mapCreatedBy(workOrder), mapperService.mapAssignedTo(workOrder), mapperService.mapComment(workOrder), mapperService.mapAttachment(workOrder));
    }

    public WorkOrderResponseForTechnician createWorkOrderResponseForTechnician(WorkOrder workOrder) {
        return new WorkOrderResponseForTechnician(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getPriority(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), mapperService.mapCreatedBy(workOrder), mapperService.mapAssignedTo(workOrder), mapperService.mapComment(workOrder), mapperService.mapAttachment(workOrder));
    }

    public WorkOrderResponseForClient createWorkOrderResponseForClient(WorkOrder workOrder) {
        return new WorkOrderResponseForClient(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(),workOrder.getCreatedAt(), workOrder.getUpdatedAt(),mapperService.mapComment(workOrder), mapperService.mapAttachment(workOrder));
    }

    public List<WorkOrderResponse> createResponseList(List<WorkOrder> workOrders, Authentication auth) {
        return workOrders.stream().map(workOrder -> createWorkOrderResponseByRole(workOrder, auth)).toList();
    }
}
