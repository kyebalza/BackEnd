package com.sparta.clone.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean likeCheck;

    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentLikeCnt;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)//null 값이 가능
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLike(Member member, Comment comment, Boolean likeCheck, Long commentLikeCnt){
        this.member = member;
        this.comment = comment;
        this.likeCheck = likeCheck;
        this.commentLikeCnt = getCommentLikeCnt();
    }
//
//    public Boolean getLikeCheck() {
//    }
}
