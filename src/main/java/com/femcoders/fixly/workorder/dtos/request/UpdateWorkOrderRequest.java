package com.femcoders.fixly.workorder.dtos.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.femcoders.fixly.workorder.entities.Status;

@JsonDeserialize(using = RoleBasedUpdateRequestDeserializer.class)
public sealed interface UpdateWorkOrderRequest permits UpdateWorkOrderRequestForAdmin, UpdateWorkOrderRequestForSupervisor, UpdateWorkOrderRequestForTechnician{
    Status status();
}
