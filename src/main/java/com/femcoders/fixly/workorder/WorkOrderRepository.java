package com.femcoders.fixly.workorder;

import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>, JpaSpecificationExecutor<WorkOrder> {
    Optional<WorkOrder> findByIdAndSupervisedBy(Long id, User supervisor);
    Optional<WorkOrder> findByIdentifierAndCreatedBy(String identifier, User user);
    Optional<WorkOrder> findByIdentifierAndAssignedToContaining(String identifier, User technician);
    List<WorkOrder> findByCreatedBy(User user);
    List<WorkOrder> findBySupervisedBy(User user);
    List<WorkOrder> findByAssignedToContaining(User user);

}