package com.sparta.clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "comment_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;


    @JoinColumn(name = "member_id",nullable = false)
    @ManyToOne(fetch =FetchType.LAZY)
    private Member member;

    public CommentLike(Comment comment, Member member) {
        this.comment = comment;
        this.member = member;
    }
}
