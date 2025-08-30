package com.femcoders.fixly.workorder.dtos;

import com.femcoders.fixly.workorder.entities.WorkOrder;
import com.femcoders.fixly.workorder.dtos.request.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderResponseForAdmin;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderResponseForClient;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderResponseForTechnician;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderSummaryResponse;
import com.femcoders.fixly.workorder.services.WorkOrderMapperServiceImpl;

public final class WorkOrderMapper {
    private WorkOrderMapper() {

    }

    public static WorkOrder createWorkOrderRequestToEntity(CreateWorkOrderRequest request) {
        return WorkOrder.builder().title(request.title().trim()).description(request.description().trim()).location(request.location().trim()).build();
    }

    public static WorkOrderSummaryResponse workOrderToDto(WorkOrder workorder) {
        return new WorkOrderSummaryResponse(workorder.getIdentifier(), workorder.getTitle(), workorder.getDescription(), workorder.getLocation(), workorder.getCreatedAt());
    }

    public static WorkOrderResponseForAdmin workOrderResponseAdminSupToDto(WorkOrder workOrder, WorkOrderMapperServiceImpl workOrderMapperService) {

        return new WorkOrderResponseForAdmin(workOrder.getId(), workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getPriority(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), workOrderMapperService.mapCreatedBy(workOrder), workOrderMapperService.mapSupervisedBy(workOrder),workOrderMapperService.mapAssignedTo(workOrder), workOrderMapperService.mapComment(workOrder), workOrderMapperService.mapAttachment(workOrder));
    }

    public static WorkOrderResponseForTechnician workOrderResponseTechToDto(WorkOrder workOrder, WorkOrderMapperServiceImpl workOrderMapperService) {
        return new WorkOrderResponseForTechnician(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getPriority(), workOrder.getSupervisionStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(), workOrderMapperService.mapCreatedBy(workOrder), workOrderMapperService.mapAssignedTo(workOrder), workOrderMapperService.mapComment(workOrder), workOrderMapperService.mapAttachment(workOrder));
    }

    public static WorkOrderResponseForClient workOrderResponseClientToDto(WorkOrder workOrder, WorkOrderMapperServiceImpl workOrderMapperService) {
        return new WorkOrderResponseForClient(workOrder.getIdentifier(), workOrder.getTitle(), workOrder.getDescription(), workOrder.getLocation(), workOrder.getStatus(), workOrder.getCreatedAt(), workOrder.getUpdatedAt(),workOrderMapperService.mapComment(workOrder), workOrderMapperService.mapAttachment(workOrder));
    }
}