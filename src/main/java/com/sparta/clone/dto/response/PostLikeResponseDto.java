package com.sparta.clone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeResponseDto {
    private boolean likeCheck;
    private Long postLikeCnt;
}
