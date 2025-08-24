package com.femcoders.fixly.attachment.dtos;

import com.femcoders.fixly.attachment.Attachment;

public final class AttachmentMapper {
    private AttachmentMapper(){
    }

    public static AttachmentResponse attachmentResponseToDto (Attachment attachment){
        return new AttachmentResponse(attachment.getId(), attachment.getFileName(), attachment.getFileURL()
        );
    }

}
