package com.femcoders.fixly.attachment.dtos;

public record AttachmentResponse(
        Long id,
        String fileName,
        String url
) {
}
