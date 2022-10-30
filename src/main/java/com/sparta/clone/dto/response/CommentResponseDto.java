package com.sparta.clone.dto.response;

import com.sparta.clone.domain.Comment;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
@Data
public class CommentResponseDto {

    private  Long id;

    private String comment;

    private String author;

    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.author = comment.getMember().getUsername();
        this.createdAt = comment.getCreatedAt();
    }
}
