package com.femcoders.fixly.workorder.services;

import com.femcoders.fixly.workorder.dtos.response.WorkOrderResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface WorkOrderService {
    List<WorkOrderResponse> getAllWorkOrders(Authentication auth);
}
