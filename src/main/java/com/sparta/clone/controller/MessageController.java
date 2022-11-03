package com.sparta.clone.controller;

import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sparta.clone.dto.response.WebSocketResponseDto.MsgContentResponseDto;
import com.sparta.clone.dto.request.WebSocketRequestDto.MsgContentRequestDto;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;



    @MessageMapping("/chat/message/{roomId}")
    @SendTo("/sub/chat/room/{roomId}")//app.js 경로와 맞물려서 수신
    @ResponseBody
    public ResponseDto<MsgContentResponseDto> requiredMessage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              MsgContentRequestDto msg,
                                                              @DestinationVariable String roomId){
        //variable 을 추출할 때는 @DestinaltionVarialbe을 사용한다.
        return ResponseDto.success(messageService.sendMessage(userDetails.getMember(), msg, roomId));
    }
}
