package com.sparta.clone.service;

import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.chat.ChatMessage;
import com.sparta.clone.domain.chat.ChatRoom;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.repository.MessageRepository;
import com.sparta.clone.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sparta.clone.dto.request.WebSocketRequestDto.MsgContentRequestDto;
import com.sparta.clone.dto.response.WebSocketResponseDto.MsgContentResponseDto;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MsgContentResponseDto sendMessage(Member member, MsgContentRequestDto msg, String roomId) {
        log.info("MessageService.sendMessage");

        ChatRoom findRoom = roomRepository.findById(Long.valueOf(roomId)).orElseThrow(
                () -> new RuntimeException("존재하지 않는 방입니다.")
        );

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 유저입니다.")
        );


        ChatMessage chatMessage = new ChatMessage(msg.getName(), findRoom, findMember, findRoom.getSubUsername());

        messageRepository.save(chatMessage);

        return new MsgContentResponseDto(findRoom.getId(), msg, findRoom, chatMessage);

    }
}

