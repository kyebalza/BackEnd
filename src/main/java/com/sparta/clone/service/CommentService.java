package com.sparta.clone.service;

import com.sparta.clone.domain.*;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.CommentLikeReqDto;
import com.sparta.clone.dto.request.CommentRequestDto;
import com.sparta.clone.dto.response.CommentLikeResDto;
import com.sparta.clone.dto.response.CommentResponseDto;
import com.sparta.clone.repository.CommentLikesRepository;
import com.sparta.clone.repository.CommentRepository;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.repository.PostRepository;
import com.sparta.clone.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final CommentLikesRepository commentLikesRepository;

    //댓글 작성
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


    //댓글 삭제
    @Transactional
    public ResponseDto<?> delete(UserDetailsImpl userDetailsImpl, Long postId, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new IllegalArgumentException("해당 아이디를 가진 댓글이 없습니다.")
        );


        checkOwner(comment, userDetailsImpl.getMember().getId());

        checkPostByPostId(comment, postId);

        commentLikesRepository.deleteByComment_Id(commentId);
        commentRepository.deleteById(commentId);


        List<Comment> commentList = commentRepository.findAllById(postId);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(Comment responseComment : commentList){



            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(responseComment.getId())
                            .profileImg(responseComment.getMember().getProfileImg())
                            .comment(responseComment.getComment())
                            .username(responseComment.getMember().getUsername())
                            .modifiedAt(responseComment.getModifiedAt())
                            .createdAt(responseComment.getCreatedAt())
                            .build()
            );
        }
        return ResponseDto.success(
                "댓글 삭제가 완료되었습니다."//commentResponseDtoList
        );
    }


    //댓글 좋아요
    public ResponseDto<?> commentLike(Long memberId, CommentLikeReqDto commentLikeReqDto) {

        Long comment_id= commentLikeReqDto.getComment_id();
        Optional<CommentLike> likes = commentLikesRepository.findByComment_IdAndMember_Id(comment_id, memberId);
        Member member = getMember(memberId);
        Comment comment = new Comment(comment_id);
        boolean likeCheck;
        if (likes.isPresent()){
            likeCheck = false;
            commentLikesRepository.delete(likes.get());
        }else {
            likeCheck = true;
            CommentLike like = new CommentLike(member, comment);
            commentLikesRepository.save(like);
        }
        Long LikeCnt = commentLikesRepository.countByComment_Id(comment_id);
        return ResponseDto.success(
                CommentLikeResDto.builder()
                        .commentlikeCnt(LikeCnt)
                        .likeCheck(likeCheck)
                        .build()
        );
    }

    //맴버 받아오기
    private Member getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
        return member;
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
