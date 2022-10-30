package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.CommentLikeReqDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@RequiredArgsConstructor
@ResponseBody
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comment/like")
    public ResponseDto<?> likes(@RequestBody @Valid CommentLikeReqDto commentLikeReqDto,
                                @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentLikeService.likes(userDetails, commentLikeReqDto.getCommentId());
    }
}