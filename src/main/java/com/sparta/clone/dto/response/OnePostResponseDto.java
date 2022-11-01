package com.sparta.clone.dto.response;

import com.sparta.clone.domain.Photo;
import com.sparta.clone.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnePostResponseDto {
    private Long Id;

    private String nickname;

    private String content;

    private List<String> postImgUrl;

    private List<CommentResponseDto> comments;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public OnePostResponseDto(Post post){
        this.Id = post.getId();
        this.nickname = post.getMember().getUsername();
        this.content = post.getContent();
        this.postImgUrl = post.getPhotos()
                .stream()
                .map(Photo::getPostImgUrl)
                .collect(Collectors.toList());
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    };
}
