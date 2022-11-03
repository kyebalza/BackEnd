package com.sparta.clone.dto.response;


import com.sparta.clone.domain.chat.ChatMessage;
import com.sparta.clone.domain.chat.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindOneResponseDto {

        // 송신자 수신자 룸ID / 내용 생성시간 수정시간
        private String username;
        private String subUsername;
        private Long roodId;
        private List<FindOneMessageResponseDto> content = new ArrayList<>();


        public FindOneResponseDto(ChatRoom findRoom, List<ChatMessage> messages) {
            this.username = findRoom.getUsername();
            this.subUsername = findRoom.getSubUsername();
            this.roodId = findRoom.getId();
            addContent(messages);
        }

        public void addContent(List<ChatMessage> messages) {
            for (ChatMessage message : messages) {
                this.content.add(new FindOneMessageResponseDto(message.getCreatedAt(),
                                                                message.getModifiedAt(),
                                                                message.getContent()));
            }
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FindOneMessageResponseDto {
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private String content;
    }
}
