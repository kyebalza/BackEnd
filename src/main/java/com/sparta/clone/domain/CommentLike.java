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

//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)//null 값이 가능
    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

//    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLike(Member member, Comment comment){
        this.member = member;
        this.comment = comment;
    }
//
//    public Boolean getLikeCheck() {
//    }
}
