package com.femcoders.fixly.comment;

import com.femcoders.fixly.attachment.Attachment;
import com.femcoders.fixly.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    @Size(min = 6)
    @Column(nullable = false)
    public String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type", nullable = false)
    public CommentType commentType;

    @CreationTimestamp
    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name = "author")
    public User author;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private Set<Attachment> attachments;
}
