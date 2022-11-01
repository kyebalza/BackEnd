package com.sparta.clone.domain.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.clone.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatRoom extends BaseTimeEntity {

    @Id
    @Column(name = "chat_room_id")
    private Long id;

    @Column
    private String chatRoomName;


    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ChatRoomMember> member;

    public ChatRoom(String name){
        this.chatRoomName = name;
    }
}
