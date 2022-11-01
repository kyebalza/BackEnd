package com.sparta.clone.domain.chat;


import com.sparta.clone.dto.request.ChatMessageRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public enum MessageType {
        STAMP, TALK, RESULT, ISSUE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String roomId;

    // Redis MessageListener 로 웹소켓을 통해 바로채팅방에 메시지를 전달해주기 위한 값을 따로 설정
    @Column
    private String username;

    @Column
    private String sender;

    @Column
    private String message;


    @Column
    private String createdAt;

    @Builder
    public ChatMessage(ChatMessageRequestDto chatMessageRequestDto, String createdAt){
        this.type = chatMessageRequestDto.getType();
        this.roomId = chatMessageRequestDto.getRoomId();
        this.username = chatMessageRequestDto.getUsername();
        this.sender = chatMessageRequestDto.getSender();
        this.message = chatMessageRequestDto.getMessage();
        this.createdAt = createdAt;
    }












}
