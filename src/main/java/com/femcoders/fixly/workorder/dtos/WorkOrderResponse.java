package com.femcoders.fixly.workorder.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CreateWorkOrderResponse(
        String identifier,
        String title,
        String description,
        String location,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {
}
