package com.sparta.clone.repository;

import com.sparta.clone.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndMemberId(Long commentId, Long memberId);


    Long countByCommentId(Long commentId);
}
