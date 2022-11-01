package com.sparta.clone.service;

import com.sparta.clone.domain.Likes;
import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.Post;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.PostLikesRequestDto;
import com.sparta.clone.dto.response.PostLikeResponseDto;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.repository.PostLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostLikesService {
    private final PostLikesRepository likesRepository;

    private final MemberRepository memberRepository;


    //맴버 받아오기
    private Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return member;
    }

    //좋아요
    public ResponseDto<?> PostLikeUp(PostLikesRequestDto postLikesRequestDto, Long memberId) {
        //1. postId 와 userEmail로 좋아요 여부 판단하기
        //boolean likes = likesRepository.existsByPostIdAndMemberId(postId, memberId);
        Optional<Likes> likes = likesRepository.findByPostIdAndMemberId(Long.parseLong(postLikesRequestDto.getPostId()), memberId);
        //Exists 메소드
        // id 만 가져오기
        Member member = getMember(memberId);
        Post post = new Post(Long.parseLong(postLikesRequestDto.getPostId()));
        boolean likeCheck;
        if (likes.isPresent()){
            likeCheck = false;
            //2-1. 있으면 삭제
            likesRepository.delete(likes.get());
            //id 만 받아와서 삭제!!
//            likeResult = "좋아요 취소";
//            return ResponseDto.success(
//                    likeResult
//            );
        }else {
            likeCheck = true;
            //2-2. 없으면 등록
            Likes like = new Likes(post, member);
            likesRepository.save(like);
//            likeResult = "좋아요 등록";
        }
        Long LikeCnt = likesRepository.countByPostId(Long.parseLong(postLikesRequestDto.getPostId()));
        return ResponseDto.success(
                PostLikeResponseDto.builder()
                        .likeCnt(LikeCnt)
                        .likeCheck(likeCheck)
                        .build()
        );

    }
}
