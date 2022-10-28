package com.sparta.clone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.clone.domain.base.BaseTimeEntity;
import com.sparta.clone.dto.request.MemberRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @ColumnDefault("https://mykeejaebucket.s3.ap-northeast-2.amazonaws.com/Pictures/9295961a-4020-4c94-8c49-3bd7ef0e62ac.png")
    private String profileImg;


    public Member(MemberRequestDto memberReqDto) {
        this.username = memberReqDto.getUsername();
        this.password = memberReqDto.getPassword();
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password, this.password);
    }
}
