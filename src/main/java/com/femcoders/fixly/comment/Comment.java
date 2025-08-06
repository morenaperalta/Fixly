package com.femcoders.fixly.comment;

import com.femcoders.fixly.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    public Long id;

    public String content;

    @Enumerated(EnumType.STRING)
    public CommentType commentType;

    public LocalDateTime createdAt;

    @ManyToOne()
    @JoinColumn(name = "author")
    public User author;
}
