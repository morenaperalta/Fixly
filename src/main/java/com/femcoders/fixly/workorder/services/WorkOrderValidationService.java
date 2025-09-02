package com.femcoders.fixly.workorder.services;

import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.WorkOrder;

public interface WorkOrderValidationService {
    void validatePermissionForField(String fieldName, String userRole, String...allowedRoles);
    void validateStatusTransition(Status currentStatus, Status newStatus, String role, WorkOrder workOrder, User currentUser);
    void validateTechnicianRole(User user);
    void validateSupervisorRole(User user);
}
