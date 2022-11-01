package com.sparta.clone.domain;

import com.sparta.clone.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merber_id",nullable = false)

    private Member member;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
//    @JsonIgnore
    private Post post;

    public Comment(Member member, Post post, String comment) {
        this.member = member;
        this.post = post;
        this.comment = comment;
    }

    public boolean checkOwnerByMemberId(Long memberId){
        return this.member.getId().equals(memberId);
    }

    public boolean checkPostByPostId(Long postId) {
        return post.getId().equals(postId);
    }
}
