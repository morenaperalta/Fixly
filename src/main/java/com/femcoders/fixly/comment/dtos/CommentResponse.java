package com.femcoders.fixly.comment.dtos;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String text,
        Long authorId,
        String authorUsername,
        LocalDateTime createdAt
) {
}
