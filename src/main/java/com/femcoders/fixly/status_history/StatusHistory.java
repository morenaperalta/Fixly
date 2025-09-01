package com.femcoders.fixly.status_history;

import com.femcoders.fixly.user.entities.User;
import com.femcoders.fixly.workorder.entities.Priority;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.SupervisionStatus;
import com.femcoders.fixly.workorder.entities.WorkOrder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "status_history")
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workorder_id", nullable = false)
    private WorkOrder workorder;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status")
    private Status previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status")
    private Status newStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_supervision_status")
    private SupervisionStatus previousSupervisionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_supervision_status")
    private SupervisionStatus newSupervisionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_priority")
    private Priority previousPriority;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_priority")
    private Priority newPriority;

    @ManyToOne
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @CreationTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
