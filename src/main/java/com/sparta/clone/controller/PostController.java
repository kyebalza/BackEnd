package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.PostRequestDto;
import com.sparta.clone.dto.response.AllPostResponseDto;
import com.sparta.clone.dto.response.PostResponseDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시글 작성
    @PostMapping("/post")
    private ResponseDto<PostResponseDto> createPost(@RequestPart(required = false, value = "file") List<MultipartFile> multipartFile,
                                                                    @RequestPart(value = "post") @Valid PostRequestDto postRequestDto,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException{
        return postService.createPost(multipartFile, postRequestDto, userDetailsImpl.getMember());
    }

    //전체 게시글 삭제
    @GetMapping("/post")
    public ResponseDto<?> getAll(){

        List<AllPostResponseDto> resDtos = postService.readAll();

        return new ResponseDto<>(true,resDtos,null);
    }
    //상세 게시글 조회
    @GetMapping("post/{postId}")
    public ResponseDto<?> getPostOne(@PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.getPostOne(postId, userDetailsImpl.getMember().getId());
    }

    //게시글 수정
    @PutMapping("/post/{postId}")
    public ResponseDto<?>updatePost(@PathVariable Long postId,
                                    @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                                    @RequestBody PostRequestDto postRequestDto){
        return postService.updatePost(postId, userDetailsImpl, postRequestDto);
    }

    //게시글 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseDto<?> deletePost(@PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return postService.deletePost(postId, userDetailsImpl);
    }

}
