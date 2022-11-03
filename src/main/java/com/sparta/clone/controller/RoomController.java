package com.sparta.clone.controller;



import com.sparta.clone.domain.chat.ChatMessage;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import static com.sparta.clone.dto.response.MemberResponseDto.*;
import static com.sparta.clone.dto.response.RoomResponseDto.*;
import static com.sparta.clone.dto.request.WebSocketRequestDto.*;



@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    // 1. 모든 방 조회
    @GetMapping("/room")
    @ResponseBody
    public ResponseDto<List<AllRoomResponseDto>> rooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("RoomController.rooms");
        return ResponseDto.success(roomService.findAllRoom(userDetails.getMember()));
    }

    //2. 특정 대화방 목록 조회

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ResponseDto<FindOneResponseDto> roomInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long roomId) {
        log.info("RoomController.roomInfo");
        return ResponseDto.success(roomService.findOneRoom(userDetails.getMember(), roomId));
    }


    //3. 새로운 채팅방 생성 (새대화 -> 팀원 추가)

    @PostMapping("/room")
    @ResponseBody
    public ResponseDto<Long> createRoom(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid CreateRoomDto createRoomDto) {
        log.info("RoomController.createRoom");
        return roomService.createRoom(userDetails.getMember(), createRoomDto);
    }


    @DeleteMapping("/room/{roomId}")
    public ResponseDto<String> deleteRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseDto.success(roomService.deleteRoom(roomId, userDetails.getUsername()));
    }

}
