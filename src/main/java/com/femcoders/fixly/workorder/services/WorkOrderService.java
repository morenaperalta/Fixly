package com.femcoders.fixly.workorder.services;

import com.femcoders.fixly.workorder.dtos.request.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.request.UpdateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderResponse;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderSummaryResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WorkOrderService {
    WorkOrderSummaryResponse createWorkOrder(CreateWorkOrderRequest request);

    List<WorkOrderResponse> getAllWorkOrders(Authentication auth);

    WorkOrderResponse getWorkOrderByIdentifier(String identifier, Authentication auth);

    WorkOrderResponse updateWorkOrder(String identifier, UpdateWorkOrderRequest request, Authentication auth);

    void deleteWorkOrder(Long id);

}
