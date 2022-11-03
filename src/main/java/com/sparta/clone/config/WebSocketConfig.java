package com.sparta.clone.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){

        registry.addEndpoint("/chat")
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        /*
        * pub -> Message Broker -> sub
        * */
        // queue와 /topic이 붙은 요청이 오면 messageBroker가 잡아서 해당 채팅방을 구독하고 있는
        //클라이언트에게 메시지를 전달하는데 STOMP 메시지의 destination 헤더는
        // @Controller 객체의 @MessageMapping 메서드로 라우팅 된다.
        // localhost:8080/sub/chat/message
        registry.enableSimpleBroker("/sub");

        //Client(publisher)에서 보낸 메세지를 받을 경로
        //Client(publisher)에서 보낸 메세지를 받을 경로
        //localhost:8080/pub/chat/message
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
