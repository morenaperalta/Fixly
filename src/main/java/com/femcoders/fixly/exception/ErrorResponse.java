package com.femcoders.fixly.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonPropertyOrder({"timestamp", "status", "error", "message", "path"})
public record ErrorResponse(
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {

    public ErrorResponse(HttpStatus httpStatus, String message, String path) {
        this(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), message, path);
    }
}
