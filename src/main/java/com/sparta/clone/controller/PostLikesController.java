package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.PostLikesRequestDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@RequestMapping("/post/like")
@RequiredArgsConstructor
public class PostLikesController {
    private final PostLikesService likesService;
    @PostMapping
    public ResponseDto<?> PostLikeUp(@RequestBody @Valid PostLikesRequestDto likeRequestDto,
                                 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return likesService.PostLikeUp(likeRequestDto, userDetailsImpl.getMember().getId());
    }
}
