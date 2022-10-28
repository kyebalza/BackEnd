package com.sparta.clone.domain;


import com.sparta.clone.domain.base.BaseTimeEntity;
import lombok.*;


import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String content;

}
