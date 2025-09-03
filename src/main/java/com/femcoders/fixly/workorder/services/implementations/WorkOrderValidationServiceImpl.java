package com.femcoders.fixly.workorder.services.implementations;

import com.femcoders.fixly.shared.exception.InsufficientPermissionsException;
import com.femcoders.fixly.user.entities.Role;
import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.user.services.UserAuthService;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import com.femcoders.fixly.workorder.services.WorkOrderValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkOrderValidationServiceImpl implements WorkOrderValidationService {
    private final UserAuthService userAuthService;

    @Override
    public void validatePermissionForField(String fieldName, String userRole, String... allowedRoles) {
        for (String allowedRole : allowedRoles) {
            if (userRole.equals(allowedRole)) {
                return;
            }
        }
        throw new InsufficientPermissionsException("update", "field");
    }

    @Override
    public void validateTechnicianRole(User user) {
        validateUserRole(user, Role.TECHNICIAN, "technician");
    }

    @Override
    public void validateSupervisorRole(User user) {
        validateUserRole(user, Role.SUPERVISOR, "supervisor");
    }

    private void validateUserRole(User user, Role expectedRole, String roleName) {
        if (user.getRole() != expectedRole) {
            throw new IllegalArgumentException("User " + user.getUsername() + " is not a " + roleName);
        }
    }

    @Override
    public void validateStatusTransition(Status currentStatus, Status newStatus, String role, WorkOrder workOrder, User user) {
        validatePermissions(newStatus, role);
        validateStatusFlow(currentStatus, newStatus);
        validateUserAssignment(newStatus, workOrder, user);
    }

    private void validatePermissions(Status newStatus, String role) {
        switch (newStatus) {
            case ASSIGNED -> validateAdminOrSupervisorPermission(role, "assign");
            case CLOSED -> validateAdminOrSupervisorPermission(role, "close");
            case IN_PROGRESS, READY_FOR_REVIEW -> validateTechnicianPermission(role);
            default -> throw new InsufficientPermissionsException("update", "work order");

        }
    }

    private void validateTechnicianPermission(String role) {
        validateRolePermission(role, userAuthService::isTechnician, "Only technicians can start work orders");
    }

    private void validateAdminOrSupervisorPermission(String role, String action) {
        if (!userAuthService.isAdmin(role) && !userAuthService.isSupervisor(role)) {
            throw new InsufficientPermissionsException(action, "work orders");
        }
    }

    private void validateRolePermission(String role, java.util.function.Predicate<String> roleChecker, String errorMessage) {
        if (!roleChecker.test(role)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void validateStatusFlow(Status currentStatus, Status newStatus) {
        switch (newStatus) {
            case ASSIGNED -> validateAssignedTransition(currentStatus);
            case IN_PROGRESS -> validateInProgressTransition(currentStatus);
            case READY_FOR_REVIEW -> validateReadyForReviewTransition(currentStatus);
            case CLOSED -> validateClosedTransition(currentStatus);
            default -> throw new IllegalArgumentException("Invalid status " + newStatus);
        }
    }

    private void validateAssignedTransition(Status currentStatus) {
        if (currentStatus != Status.PENDING) {
            throw new IllegalArgumentException("Can only assign pending work orders");
        }
    }

    private void validateInProgressTransition(Status currentStatus) {
        if (!isValidProgressTransition(currentStatus)) {
            throw new IllegalArgumentException("Can only start assigned work orders or resume for review");
        }
    }

    private void validateReadyForReviewTransition(Status currentStatus) {
        if (currentStatus != Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Can only mark in progress work orders as ready for review");
        }
    }

    private void validateClosedTransition(Status currentStatus) {
        if (currentStatus != Status.READY_FOR_REVIEW) {
            throw new IllegalArgumentException("Can only close work orders that are ready for review");
        }
    }

    private void validateUserAssignment(Status newStatus, WorkOrder workOrder, User user) {
        if (requiresAssignment(newStatus) &&
                (workOrder.getAssignedTo() == null || !workOrder.getAssignedTo().contains(user))) {
            throw new IllegalArgumentException("You're not assigned to this work order");
        }
    }

    private boolean isValidProgressTransition(Status currentStatus) {
        return currentStatus == Status.ASSIGNED || currentStatus == Status.READY_FOR_REVIEW;
    }

    private boolean requiresAssignment(Status newStatus) {
        return newStatus == Status.IN_PROGRESS || newStatus == Status.READY_FOR_REVIEW;
    }
}