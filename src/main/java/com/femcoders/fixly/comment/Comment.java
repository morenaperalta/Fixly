package com.femcoders.fixly.comment;

import com.femcoders.fixly.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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
    @Column(nullable = false)
    public CommentType commentType;

    @CreationTimestamp
    public LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name = "author")
    public User author;
}
