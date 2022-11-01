package com.sparta.clone.repository;

import com.sparta.clone.domain.Likes;
import com.sparta.clone.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<Likes,Long> {
    Long countByPostId(Long postId);

    Optional<Likes> findByPostIdAndMemberId(Long postId, Long memberId);


    void deleteLikesByPostId(Long postId);
}
