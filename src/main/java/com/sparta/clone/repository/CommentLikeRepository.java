package com.sparta.clone.repository;

import com.sparta.clone.domain.Comment;
import com.sparta.clone.domain.CommentLike;
import com.sparta.clone.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findAllByCommentAndMember(Comment comment, Member member);
}
