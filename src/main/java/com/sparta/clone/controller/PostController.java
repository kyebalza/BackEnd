package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.response.PostResponseDto;
import com.sparta.clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

//    @PostMapping("/post")
//    private ResponseEntity<ResponseDto<PostResponseDto>> createPost(@RequestPart )
}
