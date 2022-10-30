package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.response.AllPostResponseDto;
import com.sparta.clone.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPostController {
    private final PostService postService;
    @GetMapping("/post/like")
    public ResponseDto<?> getAll(){

        List<AllPostResponseDto> resDtos = postService.readAll();

        return new ResponseDto<>(true,resDtos,null);
    }
}
