package com.femcoders.fixly.workorder.dtos.request;

import com.femcoders.fixly.workorder.entities.Priority;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.SupervisionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UpdateWorkOrderRequestForSupervisor(
        @Schema(description = "New priority for the work order. Only SUPERVISOR and ADMIN can update this field.") Priority priority,

        @Schema(description = "New status for the work order. Permissions and valid transitions depend on user role.") Status status,

        @Schema(description = "New supervision status. Only SUPERVISOR and ADMIN can update this field.") SupervisionStatus supervisionStatus,

        @Schema(description = "List of technician IDs to assign. Only SUPERVISOR and ADMIN can update this field.") List<Long> technicianIds) implements UpdateWorkOrderRequest {
}
