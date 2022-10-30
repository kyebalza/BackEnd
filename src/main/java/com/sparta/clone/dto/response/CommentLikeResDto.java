package com.sparta.clone.dto.response;

import com.sparta.clone.domain.CommentLike;
import lombok.Data;

@Data
public class CommentLikeResDto {
    private Long commentId;

    private boolean likeCheck;

    public CommentLikeResDto(CommentLike commentLike){

        this.commentId = commentLike.getComment().getId();
        this.likeCheck = commentLike.;
    }
}
