package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.CommentLikeReqDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comment/like")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseDto<?> likes(@RequestBody @Valid CommentLikeReqDto commentLikeReqDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return commentLikeService.likes(commentLikeReqDto, userDetailsImpl.getMember().getId());
    }
}