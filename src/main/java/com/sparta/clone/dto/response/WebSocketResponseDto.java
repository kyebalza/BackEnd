package com.sparta.clone.dto.response;


import com.sparta.clone.domain.chat.ChatMessage;
import com.sparta.clone.domain.chat.ChatRoom;
import com.sparta.clone.dto.request.WebSocketRequestDto.MsgContentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



public class WebSocketResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MsgContentResponseDto {

        private Long roomId;
        private MsgContentRequestDto msg;
        private ChatRoom room;
        private ChatMessage message;

    }
}
