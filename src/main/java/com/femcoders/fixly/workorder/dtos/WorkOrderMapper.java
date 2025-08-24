package com.femcoders.fixly.workorder.dtos;

import com.femcoders.fixly.attachment.dtos.AttachmentMapper;
import com.femcoders.fixly.attachment.dtos.AttachmentResponse;
import com.femcoders.fixly.comment.dtos.CommentMapper;
import com.femcoders.fixly.comment.dtos.CommentResponse;
import com.femcoders.fixly.user.dtos.UserMapper;
import com.femcoders.fixly.user.dtos.UserSummaryResponse;
import com.femcoders.fixly.workorder.WorkOrder;

import java.util.List;

public final class WorkOrderMapper {
    private WorkOrderMapper(){

    }
    public static WorkOrder createWorkOrderRequestToEntity(CreateWorkOrderRequest request) {
        return WorkOrder.builder()
                .title(request.title().trim())
                .description(request.description().trim())
                .location(request.location().trim())
                .build();
    }

    public static WorkOrderResponse workOrderToDto(WorkOrder workorder) {
        return new WorkOrderResponse(
                workorder.getIdentifier(),
                workorder.getTitle(),
                workorder.getDescription(),
                workorder.getLocation(),
                workorder.getCreatedAt()
        );
    }

    public static WorkOrderResponseForAdminAndSupervisor workOrderResponseAdminSupToDto(WorkOrder workOrder) {
        UserSummaryResponse createdBy = workOrder.getCreatedBy() != null
                ? UserMapper.userSummaryResponseToDto(workOrder.getCreatedBy())
                : null;

        List<UserSummaryResponse> assignedTo = workOrder.getAssignedTo() != null
                ? workOrder.getAssignedTo().stream()
                .map(UserMapper::userSummaryResponseToDto)
                .toList()
                : null;

        List<CommentResponse> comments = workOrder.getComments() != null
                ? workOrder.getComments().stream()
                .map(CommentMapper::commentResponsetoDto)
                .toList()
                : null;

        List<AttachmentResponse> attachments = workOrder.getAttachments() != null
                ? workOrder.getAttachments().stream()
                .map(AttachmentMapper::attachmentResponseToDto)
                .toList()
                : null;

        return new WorkOrderResponseForAdminAndSupervisor(
                workOrder.getId(),
                workOrder.getIdentifier(),
                workOrder.getTitle(),
                workOrder.getDescription(),
                workOrder.getLocation(),
                workOrder.getStatus(),
                workOrder.getPriority(),
                workOrder.getSupervisionStatus(),
                workOrder.getCreatedAt(),
                workOrder.getUpdatedAt(),
                createdBy,
                assignedTo,
                comments,
                attachments
        );
    }

    public static WorkOrderResponseForTechnician workOrderResponseTechToDto(WorkOrder workOrder) {
        UserSummaryResponse createdBy = workOrder.getCreatedBy() != null
                ? UserMapper.userSummaryResponseToDto(workOrder.getCreatedBy())
                : null;

        List<UserSummaryResponse> assignedTo = workOrder.getAssignedTo() != null
                ? workOrder.getAssignedTo().stream()
                .map(UserMapper::userSummaryResponseToDto)
                .toList()
                : null;

        List<CommentResponse> comments = workOrder.getComments() != null
                ? workOrder.getComments().stream()
                .map(CommentMapper::commentResponsetoDto)
                .toList()
                : null;

        List<AttachmentResponse> attachments = workOrder.getAttachments() != null
                ? workOrder.getAttachments().stream()
                .map(AttachmentMapper::attachmentResponseToDto)
                .toList()
                : null;

        return new WorkOrderResponseForTechnician(
                workOrder.getIdentifier(),
                workOrder.getTitle(),
                workOrder.getDescription(),
                workOrder.getLocation(),
                workOrder.getStatus(),
                workOrder.getPriority(),
                workOrder.getSupervisionStatus(),
                workOrder.getCreatedAt(),
                workOrder.getUpdatedAt(),
                createdBy,
                assignedTo,
                comments,
                attachments
        );
    }
}