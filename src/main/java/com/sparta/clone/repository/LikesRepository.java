package com.sparta.clone.repository;

import com.sparta.clone.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    //좋아요 여부 받아오기
    Optional<Likes> findByPostIdAndMemberId(Long postId, Long memberId);

    //게시글의 좋아요 수 가져오기
    Long countByPostId(Long postId);

}
