package com.sparta.clone.service;

import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.chat.ChatMessage;
import com.sparta.clone.domain.chat.ChatRoom;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.WebSocketRequestDto;
import com.sparta.clone.dto.response.RoomResponseDto;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.repository.MessageRepository;
import com.sparta.clone.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import static com.sparta.clone.dto.response.MemberResponseDto.*;
import static com.sparta.clone.dto.response.RoomResponseDto.*;
import static com.sparta.clone.dto.request.WebSocketRequestDto.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;

    /**
     * 모든 대화 목록 불러오기
     */
    public List<AllRoomResponseDto> findAllRoom(Member member) {
        // 로그인한 사용자의 정보를 로드
        Member findMember = isExistMember(member);


        // 검색 조건: 채팅방 최근 생성 순 + 해당 사용자와 일치하는 메시지의 desUsername
        // 로그인한 사용자와 동일한 룸의 desUsername을 읽어온다

        List<ChatRoom> rooms = roomRepository.findByUsername(findMember.getUsername());
        List<AllRoomResponseDto> responseDto = new ArrayList<>();

        for (ChatRoom room : rooms) {
            responseDto.add(new AllRoomResponseDto(room.getId(), room.getSubUsername()));
        }
        return responseDto;
    }

    /**
     * 특정 대화 방 조회하기
     */
    public FindOneResponseDto findOneRoom(Member member, Long roomId) {
        isExistMember(member);

        // 1. 특정 대화 방 찾기
        ChatRoom findRoom = roomRepository.findByUsernameAndId(member.getUsername(), roomId).orElseThrow(
                () -> new RuntimeException("존재하지 않는 방입니다.")
        );

        // 2. 특정 대화방의 내용을 찾기
        List<ChatMessage> messages = messageRepository.findByRoomId(findRoom.getId());

        // 송신자 수신자 내용 생성시간 수정시간 룸ID
        return new FindOneResponseDto(findRoom, messages);
    }

    /**
     * 대화 방 생성하기
     */
    @Transactional
    public ResponseDto<Long> createRoom(Member member, CreateRoomDto createRoomDto) {
        isExistMember(member);

        log.info("===============");
        log.info("createRoomDto.getDesEmail() = {}", createRoomDto.getDesEmail());
        log.info("member.getEmail() = {}", member.getUsername());
        log.info("===============");

        Member findMember = memberRepository.findByUsername(createRoomDto.getDesEmail()).orElseThrow(
                () -> new RuntimeException("존재하지 않은 사용자 입니다.")
        );


        ChatRoom room = new ChatRoom(findMember.getUsername(), member.getUsername());
        roomRepository.save(room);

        return ResponseDto.success(room.getId());
    }

    private Member isExistMember(Member member) {
        Member findMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(
                () -> new RuntimeException("로그인이 필요합니다.")
        );
        return findMember;
    }

    @Transactional
    public String deleteRoom(Long roomId, String username) {
        Member Username = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("권한이 없는 유저입니다."));
        roomRepository.deleteById(roomId);
        return "룸 삭제가 완료되었습니다";
    }
}