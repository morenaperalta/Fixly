package com.femcoders.fixly.workorder.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.femcoders.fixly.attachment.dtos.AttachmentResponse;
import com.femcoders.fixly.comment.dtos.CommentResponse;
import com.femcoders.fixly.workorder.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record WorkOrderResponseForClient(
        String identifier,
        String title,
        String description,
        String location,
        Status status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        List<CommentResponse> comments,
        List<AttachmentResponse> attachments
) implements WorkOrderResponse {
}
