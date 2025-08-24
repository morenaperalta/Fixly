package com.femcoders.fixly.comment.dtos;

import com.femcoders.fixly.comment.Comment;

public final class CommentMapper {
    private CommentMapper(){

    }
    public static CommentResponse commentResponsetoDto(Comment comment) {
        return new CommentResponse(
                comment.id,
                comment.content,
                comment.author != null ? comment.author.getId() : null,
                comment.author != null ? comment.author.getUsername() : null,
                comment.createdAt
        );
    }
}
