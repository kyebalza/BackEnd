package com.sparta.clone.dto.response;

import com.sparta.clone.domain.Comment;
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

    private String profileImg;
    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;


    //추가
    private boolean commentLikeCheck;
    private Long commentLikeCnt;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.username = comment.getMember().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
