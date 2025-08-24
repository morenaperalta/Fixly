package com.femcoders.fixly.workorder;

import com.femcoders.fixly.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long>, JpaSpecificationExecutor<WorkOrder> {
    boolean existsByIdentifier(String identifier);
    Optional<WorkOrder> findByIdAndSupervisedBy(Long id, User supervisor);
    List<WorkOrder> findByCreatedBy(User user);
    List<WorkOrder> findBySupervisedBy(User user);
    List<WorkOrder> findByAssignedToContaining(User user);

}