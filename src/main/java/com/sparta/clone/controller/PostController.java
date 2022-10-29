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

    @PostMapping("/post")
    private ResponseDto<PostResponseDto> createPost(@RequestPart(required = false, value = "file") List<MultipartFile> multipartFile,
                                                                    @RequestPart(value = "post") @Valid PostRequestDto postRequestDto,
                                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        return postService.createPost(multipartFile, postRequestDto, userDetails.getMember());
    }
    @GetMapping("/post")
    public ResponseDto<?> getAll(){

        List<AllPostResponseDto> resDtos = postService.readAll();

        return new ResponseDto<>(true,resDtos,null);
    }
}
