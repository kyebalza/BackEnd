package com.sparta.clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private int id;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Comment comment;

    @JsonIgnoreProperties({"postList"}) //post -> user -> likesList -> user -> postList 무한 참조 막기 위함
    @JoinColumn(name = "user_id")
    @ManyToOne
    private Member member;

    public CommentLike(Comment comment, Member member) {
        this.comment = comment;
        this.member = member;
    }
}
