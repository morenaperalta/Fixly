package com.femcoders.fixly.workorder.services;

import com.femcoders.fixly.attachment.dtos.AttachmentResponse;
import com.femcoders.fixly.comment.dtos.CommentResponse;
import com.femcoders.fixly.user.dtos.response.UserSummaryResponse;
import com.femcoders.fixly.workorder.entities.WorkOrder;

import java.util.List;

public interface WorkOrderMapperService {
    UserSummaryResponse mapCreatedBy(WorkOrder workOrder);

    List<UserSummaryResponse> mapAssignedTo(WorkOrder workOrder);

    List<CommentResponse> mapComment(WorkOrder workOrder);

    List<AttachmentResponse> mapAttachment(WorkOrder workOrder);

}
