package com.femcoders.fixly.attachment;

import com.femcoders.fixly.comment.Comment;
import com.femcoders.fixly.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "attachments")
public class Attachment {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotBlank
   @Column(name = "file_name", nullable = false)
   private String fileName;

   @NotBlank
   @Column(name = "file_url", nullable = false)
   private String fileURL;

   @Column(name = "file_type")
   private String fileType;

   @ManyToOne
   @JoinColumn(name = "uploaded_by")
   private User uploadedBy;

   @ManyToOne
   @JoinColumn(name = "comment_id")
   private Comment comment;
}
