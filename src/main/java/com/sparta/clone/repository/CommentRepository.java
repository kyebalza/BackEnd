package com.sparta.clone.repository;

import com.sparta.clone.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.post.id = :postId")
    List<Comment> findAllById(@Param("postId") Long postId);

//    @Modifying
//    @Query("delete from Comment c where c.post.id = :postId")
//    void deleteCommentByPostId(@Param("postId")Long postId);
}
