package com.sparta.clone.service;

import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.CommentLike;
import com.sparta.clone.domain.Member;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.CommentLikeReqDto;
import com.sparta.clone.dto.response.CommentLikeResDto;
import com.sparta.clone.repository.CommentLikeRepository;
import com.sparta.clone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    private final MemberRepository memberRepository;

    private Member getMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalArgumentException("해당 아이디를 가진 사용자를 찾을 수 없습니다.")
                        );
        return member;
    }

    public ResponseDto<?> likes(CommentLikeReqDto commentLikeReqDto, Long memberId) {
        //1. postId 와 userEmail로 좋아요 여부 판단하기
        //boolean likes = likesRepository.existsByPostIdAndMemberId(postId, memberId);
        Optional<CommentLike> commentLikes = commentLikeRepository.findByCommentIdAndMemberId(commentLikeReqDto.getComment_id(), memberId);
        //Exists 메소드
        // id 만 가져오기
        Member member = getMember(memberId);
        Comment comment = new Comment(commentLikeReqDto.getComment_id());
        boolean likeCheck;
        if (commentLikes.isPresent()){
            likeCheck = false;
            //2-1. 있으면 삭제
            commentLikeRepository.delete(commentLikes.get());
            //id 만 받아와서 삭제!!
//            likeResult = "좋아요 취소";
//            return ResponseDto.success(
//                    likeResult
//            );
        }else {
            likeCheck = true;
            //2-2. 없으면 등록
            CommentLike like = new CommentLike(member,comment);
            commentLikeRepository.save(like);
//            likeResult = "좋아요 등록";
        }
        Long CommentLikeCnt = commentLikeRepository.countByCommentId(commentLikeReqDto.getComment_id());
        return ResponseDto.success(
                CommentLikeResDto.builder()
                        .commentlikeCnt(CommentLikeCnt)
                        .likeCheck(likeCheck)
                        .build()
        );

    }
}
