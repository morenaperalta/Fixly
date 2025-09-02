package com.femcoders.fixly.workorder.services.implementations;

import com.femcoders.fixly.attachment.dtos.AttachmentMapper;
import com.femcoders.fixly.attachment.dtos.AttachmentResponse;
import com.femcoders.fixly.comment.dtos.CommentMapper;
import com.femcoders.fixly.comment.dtos.CommentResponse;
import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.user.dtos.response.UserSummaryResponse;
import com.femcoders.fixly.user.services.UserAuthService;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import com.femcoders.fixly.workorder.services.WorkOrderMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkOrderMapperServiceImpl implements WorkOrderMapperService {
    private final UserAuthService userAuthService;

    public UserSummaryResponse mapCreatedBy(WorkOrder workOrder) {
        return workOrder.getCreatedBy() != null ? UserMapper.userSummaryResponseToDto(workOrder.getCreatedBy()) : null;
    }

    public UserSummaryResponse mapSupervisedBy(WorkOrder workOrder) {
        return workOrder.getSupervisedBy() != null ? UserMapper.userSummaryResponseToDto(workOrder.getSupervisedBy()) : null;
    }

    public List<UserSummaryResponse> mapAssignedTo(WorkOrder workOrder) {
        return workOrder.getAssignedTo() != null ? workOrder.getAssignedTo().stream().map(UserMapper::userSummaryResponseToDto).toList() : null;
    }

    public List<CommentResponse> mapComment(WorkOrder workOrder) {
        return workOrder.getComments() != null ? workOrder.getComments().stream().map(CommentMapper::commentResponsetoDto).toList() : null;
    }

    public List<AttachmentResponse> mapAttachment(WorkOrder workOrder) {
        return workOrder.getAttachments() != null ? workOrder.getAttachments().stream().map(AttachmentMapper::attachmentResponseToDto).toList() : null;
    }
}
