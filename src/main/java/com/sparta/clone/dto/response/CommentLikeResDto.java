package com.sparta.clone.dto.response;

import com.sparta.clone.domain.CommentLike;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeResDto {
    private Boolean likeCheck;
    private Long commentlikeCnt;


    //??
//    public CommentLikeResDto(CommentLike commentLike){
////        this.likeCheck = commentLike.getLikeCheck();
//        this.commentlikeCnt = commentLike.getCommentLikeCnt();
//
//    }
}
