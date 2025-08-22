package com.femcoders.fixly.workorder.dtos;
import com.femcoders.fixly.workorder.WorkOrder;

public class WorkOrderMapper {
    public static WorkOrder createWorkOrderRequestToEntity(CreateWorkOrderRequest request) {
        return WorkOrder.builder().title(request.title().trim()).description(request.description().trim()).location(request.location().trim()).build();
    }

    public static CreateWorkOrderResponse createWorkOrderResponseToDto(WorkOrder workorder){
        return new CreateWorkOrderResponse(workorder.getIdentifier(), workorder.getTitle(), workorder.getDescription(), workorder.getLocation(), workorder.getCreatedAt());
    }
}
