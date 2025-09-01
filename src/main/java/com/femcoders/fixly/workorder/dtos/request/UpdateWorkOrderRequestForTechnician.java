package com.femcoders.fixly.workorder.dtos.request;

import com.femcoders.fixly.workorder.entities.Status;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateWorkOrderRequestForTechnician(
        @Schema(description = "New status for the work order. Permissions and valid transitions depend on user role.") Status status) implements UpdateWorkOrderRequest {
}
