package com.femcoders.fixly.workorder.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.femcoders.fixly.attachment.dtos.AttachmentResponse;
import com.femcoders.fixly.comment.dtos.CommentResponse;
import com.femcoders.fixly.user.dtos.response.UserSummaryResponse;
import com.femcoders.fixly.workorder.enums.Priority;
import com.femcoders.fixly.workorder.enums.Status;
import com.femcoders.fixly.workorder.enums.SupervisionStatus;

import java.time.LocalDateTime;
import java.util.List;

public record WorkOrderResponseForAdmin(
        Long id,
        String identifier,
        String title,
        String description,
        String location,
        Status status,
        Priority priority,
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

