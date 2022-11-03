package com.sparta.clone.repository;

import com.sparta.clone.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByUsername(String username);

    Optional<ChatRoom> findByUsernameAndId(String username, Long roomId);
}
