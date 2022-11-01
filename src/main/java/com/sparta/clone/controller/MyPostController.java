package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.response.AllPostResponseDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPostController {
    private final PostService postService;
    @GetMapping("/mypost")
    public ResponseDto<?> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.getMyPost(userDetailsImpl.getMember().getId());
    }
}
