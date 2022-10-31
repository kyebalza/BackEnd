package com.sparta.clone.dto.request;

import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.CommentLike;
import com.sparta.clone.domain.Member;
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
