package com.sparta.clone.dto.response;

import com.sparta.clone.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllPostResponseDto {

    private Long id;

    private String nickname;

    private String content;

    private String postImgUrl;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
    //Post타입에서 PostResDto타입으로 가져올꺼기 때문에  Post post를 인자로 받아준다.
    public AllPostResponseDto(Post post){
        this.id = post.getId();
        this.nickname = post.getMember().getUsername();
        this.content = post.getContent();
        this.postImgUrl = post.getImgUrl();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
