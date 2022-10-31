package com.sparta.clone.service;

import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.CommentLike;
import com.sparta.clone.domain.Member;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.response.CommentLikeResDto;
import com.sparta.clone.repository.CommentLikeRepository;
import com.sparta.clone.repository.CommentRepository;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    private Member getMember(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(
                ()-> new IllegalArgumentException("해당 아이디를 가진 사용자를 찾을 수 없습니다.")
                        );
        return member;
    }

    public ResponseDto<?> likes(Long commentId, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        Optional<CommentLike> commentLike = commentLikeRepository.findAllByCommentAndMember(comment, userDetails.getMember());

        boolean likeCheck;
        if(commentLike.isPresent()){
            likeCheck = false;
            commentLikeRepository.deleteById(commentLike.get().getComment().getId());
        }else{
            likeCheck = true;
            CommentLike commentLikes = new CommentLike(comment, userDetails.getMember());

            commentLikeRepository.save(commentLikes);
        }
        Long CommentLikeCnt = commentLikeRepository.countById(commentId);
        return ResponseDto.success(
                CommentLikeResDto.builder()
                        .commentLikeCnt(CommentLikeCnt)
                        .likeCheck(likeCheck)
                        .build()
        );
    }
}
