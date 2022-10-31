package com.sparta.clone.repository;

import com.sparta.clone.domain.CommentLike;
import com.sparta.clone.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLike, Long> {

    //좋아요 여부 받아오기
    Optional<CommentLike> findByComment_IdAndMember_Id(Long commnetId, Long memberId);

    //게시글의 좋아요 수 가져오기
    Long countByComment_Id(Long postId);

}
