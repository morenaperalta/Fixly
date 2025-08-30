package com.femcoders.fixly.workorder.dtos;

import java.time.LocalDateTime;

public sealed interface WorkOrderResponse permits WorkOrderSummaryResponse, WorkOrderResponseForClient, WorkOrderResponseForTechnician, WorkOrderResponseForAdmin{
    String identifier();
    String title();
    String description();
    String location();
    LocalDateTime createdAt();
}
