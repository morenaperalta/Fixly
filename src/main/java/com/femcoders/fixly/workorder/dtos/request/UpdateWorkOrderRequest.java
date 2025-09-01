package com.femcoders.fixly.workorder.dtos.request;

import com.femcoders.fixly.workorder.entities.Status;

public sealed interface UpdateWorkOrderRequest permits UpdateWorkOrderRequestForAdmin, UpdateWorkOrderRequestForSupervisor, UpdateWorkOrderRequestForTechnician{
    Status status();
}
