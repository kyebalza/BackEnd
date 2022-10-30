package com.sparta.clone.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.swing.plaf.metal.MetalIconFactory;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Photo {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
//    @Column(nullable = false)
//    private Long postId;

    @Column(nullable = false)
    private String postImgUrl;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;


    public Photo(String photoImgUrl, Post post){
        this.postImgUrl = photoImgUrl;
        this.post = post;

    }

}
