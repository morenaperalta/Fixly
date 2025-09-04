package com.femcoders.fixly.workorder.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.femcoders.fixly.attachment.dtos.AttachmentResponse;
import com.femcoders.fixly.comment.dtos.CommentResponse;
import com.femcoders.fixly.user.dtos.response.UserSummaryResponse;
import com.femcoders.fixly.workorder.entities.Priority;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.SupervisionStatus;

import java.time.LocalDateTime;
import java.util.List;

public record WorkOrderResponseForTechnician(
        String identifier,
        String title,
        String description,
        String location,
        Priority priority,
        Status status,
        SupervisionStatus supervisionStatus,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        UserSummaryResponse createdBy,
        List<UserSummaryResponse> assignedTo,
        List<CommentResponse> comments,
        List<AttachmentResponse> attachments
) implements WorkOrderResponse {
}

