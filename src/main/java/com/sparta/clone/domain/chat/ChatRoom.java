package com.sparta.clone.domain.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sparta.clone.domain.base.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String subUsername;

    @NotBlank
    private String username;

    public ChatRoom(String subUsername, String username){
        this.subUsername = subUsername;
        this.username = username;
    }
}
