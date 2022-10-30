package com.sparta.clone.repository;

import com.sparta.clone.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByPostId(Long id);
    void deleteAllByPost_id(Long postId);
}
