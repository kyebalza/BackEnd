package com.sparta.clone.domain;


import com.sparta.clone.domain.base.BaseTimeEntity;
import lombok.*;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String content;

    public Post(Long id) {

        this.id = id;
    }

    @OneToMany(mappedBy = "post",fetch = EAGER)
    private List<Photo> photos= new ArrayList<>();

    public void updatePost(String content) {
        this.content = content;
    }

    public boolean checkOwnerByMemberId(Long memberId) {
        //this. 이 게시물 주인의 id  //인자로 받는 memberId
        return this.member.getId().equals(memberId);

    }
}
