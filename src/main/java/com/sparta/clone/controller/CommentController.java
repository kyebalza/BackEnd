package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.CommentRequestDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{post_id}/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseDto<?> create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @PathVariable("post_id") Long postId,
                                 @RequestBody CommentRequestDto dto){
        commentService.create(postId,dto,userDetails.getMember().getId());
        return new ResponseDto<>(true,dto,null);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseDto<?> delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                 @PathVariable("post_id") Long postId,
                                 @PathVariable("comment_id") Long commentId){

        return commentService.delete(userDetailsImpl, postId, commentId);
    }


}
