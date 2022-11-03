package com.sparta.clone.domain.chat;


import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.base.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    //송신자 publisher
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    //수신자 subscriber

    @Column(nullable = false)
    private String subUsername;

    public ChatMessage(String content, ChatRoom room, Member member, String subUsername) {
        this.content = content;
        this.room = room;
        this.member = member;
        this.subUsername = subUsername;
    }
}

