package com.sparta.clone.service;

import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.Post;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.CommentRequestDto;
import com.sparta.clone.dto.response.CommentResponseDto;
import com.sparta.clone.repository.CommentRepository;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.repository.PostRepository;
import com.sparta.clone.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void create(Long postId, CommentRequestDto dto, Long usernameId){

        Member username = memberRepository.findById(usernameId).orElseThrow(
                ()-> new IllegalArgumentException("해당 아이디를 가진 멤버의 아이디가 없습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 아이디를 가진 댓글이 존재하지 않습니다.")
        );

        Comment comment = dto.toEntity(username,post);
        commentRepository.save(comment);
    }


    @Transactional
    public ResponseDto<?> delete(UserDetailsImpl userDetailsImpl, Long postId, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 아이디를 가진 댓글이 없습니다.")
        );

        checkOwner(comment, userDetailsImpl.getMember().getId());

        checkPostByPostId(comment, postId);

        commentRepository.deleteById(commentId);

        List<Comment> commentList = commentRepository.findAllById(postId);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment responseComment : commentList){
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(responseComment.getId())
                            .comments(responseComment.getComment())
                            .createdAt(responseComment.getCreatedAt())
                            .build()
            );
        }
        return ResponseDto.success(
                commentResponseDtoList
        );
    }

    private void checkOwner(Comment comment, Long memberId){
        if(!comment.checkOwnerByMemberId(memberId)){
            throw new IllegalArgumentException("회원님이 작성한 댓글이 아닙니다.");
        }
    }

    private void checkPostByPostId(Comment comment, Long postId){
        if(!comment.checkPostByPostId(postId)){
            throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다.");
        }
    }
}
