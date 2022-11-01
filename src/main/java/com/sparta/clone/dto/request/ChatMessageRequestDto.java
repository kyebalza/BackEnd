package com.sparta.clone.dto.request;

import com.sparta.clone.domain.chat.ChatMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {

    private ChatMessage.MessageType type;
    private String roomId;
    private String username;
    private String sender;
    private String message;
}
