package com.femcoders.fixly.workorder.dtos;

import com.femcoders.fixly.workorder.WorkOrder;
import com.femcoders.fixly.workorder.services.WorkOrderMapperService;

public final class WorkOrderMapper {
    private WorkOrderMapper() {

    }

    public static WorkOrder createWorkOrderRequestToEntity(CreateWorkOrderRequest request) {
        return WorkOrder.builder().title(request.title().trim()).description(request.description().trim()).location(request.location().trim()).build();
    }

    public static WorkOrderResponse workOrderToDto(WorkOrder workorder) {
        return new WorkOrderResponse(workorder.getIdentifier(), workorder.getTitle(), workorder.getDescription(), workorder.getLocation(), workorder.getCreatedAt());
    }

    public static WorkOrderResponseForAdminAndSupervisor workOrderResponseAdminSupToDto(WorkOrder workOrder, WorkOrderMapperService workOrderMapperService) {

        return new WorkOrderResponseForAdminAndSupervisor(workOrder.getId(), workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getPriority(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), workOrderMapperService.mapCreatedBy(workOrder), workOrderMapperService.mapAssignedTo(workOrder), workOrderMapperService.mapComment(workOrder), workOrderMapperService.mapAttachment(workOrder));
    }

    public static WorkOrderResponseForTechnician workOrderResponseTechToDto(WorkOrder workOrder, WorkOrderMapperService workOrderMapperService) {
        return new WorkOrderResponseForTechnician(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getPriority(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), workOrderMapperService.mapCreatedBy(workOrder), workOrderMapperService.mapAssignedTo(workOrder), workOrderMapperService.mapComment(workOrder), workOrderMapperService.mapAttachment(workOrder));
    }

    public static WorkOrderResponseForClient workOrderResponseClientToDto(WorkOrder workOrder, WorkOrderMapperService workOrderMapperService) {

        return new WorkOrderResponseForClient(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), workOrderMapperService.mapAttachment(workOrder));
    }
}