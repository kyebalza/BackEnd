package com.sparta.clone.dto.response;

import com.sparta.clone.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private String content;
    private List<PhotoResponseDto> postImgUrl;
}
