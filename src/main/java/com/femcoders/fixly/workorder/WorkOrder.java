package com.femcoders.fixly.workorder;

import com.femcoders.fixly.attachment.Attachment;
import com.femcoders.fixly.comment.Comment;
import com.femcoders.fixly.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "workorder")
public class WorkOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "supervision_status")
    private SupervisionStatus supervisionStatus;

    @NotBlank
    @Size(min = 3)
    @Column(nullable = false)
    private String location;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToMany
    @JoinTable(name = "workorder_assigned_to", joinColumns = @JoinColumn(name = "workorder_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> assignedTo;

    @ManyToOne
    @JoinColumn(name = "supervised_by")
    private User supervisedBy;

    @OneToMany(mappedBy = "workorder",  cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany
    private Set<Attachment> attachments;
}
