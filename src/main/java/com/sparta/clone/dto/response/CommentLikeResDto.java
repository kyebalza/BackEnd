package com.sparta.clone.dto.response;

import com.sparta.clone.domain.CommentLike;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeResDto {

    private boolean likeCheck;
    private Long commentLikeCnt;


}
