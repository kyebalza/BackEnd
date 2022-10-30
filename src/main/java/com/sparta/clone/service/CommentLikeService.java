package com.sparta.clone.service;

import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.CommentLike;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.repository.CommentLikeRepository;
import com.sparta.clone.repository.CommentRepository;
import com.sparta.clone.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public ResponseDto<?> likes(UserDetailsImpl userDetails, Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
        );

        Optional<CommentLike> commentLike = commentLikeRepository.findAllByCommentAndMember(comment, userDetails.getMember());

        if(commentLike.isPresent()){
            commentLikeRepository.deleteById(commentLike.get().getComment().getId());
        }else{
            CommentLike commentLikes = new CommentLike(comment, userDetails.getMember());

            commentLikeRepository.save(commentLikes);
        }
        return null;
    }
}
