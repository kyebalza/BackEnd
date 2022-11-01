package com.sparta.clone.dto.request;

import lombok.Data;


@Data
public class CommentLikeReqDto {

    private Long comment_id;

    //??
//    private Long commentLikeCnt;

//    public CommentLike toEntity(Member member, Comment comment){
//        return new CommentLike(member,comment);
//    }
}
