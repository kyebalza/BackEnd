package com.sparta.clone.service;

import com.sparta.clone.Exception.ErrorCode;
import com.sparta.clone.Exception.GlobalException;
import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.RefreshToken;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.LoginRequestDto;
import com.sparta.clone.dto.request.MemberRequestDto;
import com.sparta.clone.dto.response.MemberResponseDto;
import com.sparta.clone.repository.MemberRepository;
import com.sparta.clone.repository.RefreshTokenRepository;
import com.sparta.clone.security.jwt.JwtUtil;
import com.sparta.clone.security.jwt.TokenDto;
import com.sparta.clone.utils.ValidateCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ValidateCheck validateCheck;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    //회원가입
    @Transactional
    public ResponseEntity<ResponseDto<MemberResponseDto>> signup(MemberRequestDto memberReqDto) {

        // username 중복 검사
        usernameDuplicateCheck(memberReqDto);

        // 비빌번호 확인 & 비빌번호 불일치
        if(!memberReqDto.getPassword().equals(memberReqDto.getPasswordConfirm())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD_CONFIRM);
        }

        Member member = Member.builder()
                .username(memberReqDto.getUsername())
                .password(passwordEncoder.encode(memberReqDto.getPassword()))
                .build();
        memberRepository.save(member);
        return ResponseEntity.ok().body(ResponseDto.success(
                MemberResponseDto.builder()
                        .username(member.getUsername())
                        .profileImg(member.getProfileImg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        ));

    }

    public void usernameDuplicateCheck(MemberRequestDto memberReqDto) {
        if(memberRepository.findByUsername(memberReqDto.getUsername()).isPresent()){
            throw new GlobalException(ErrorCode.DUPLICATE_MEMBER_ID);
            // ex) return ResponseDto.fail()
        }
    }

    //로그인
    @Transactional
    public ResponseEntity<ResponseDto<MemberResponseDto>> login(LoginRequestDto loginReqDto, HttpServletResponse response) {

        Member member = validateCheck.isPresentMember(loginReqDto.getUsername());

        //사용자가 있는지 확인
        if(null == member){
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }
        //비밀번호가 맞는지 확인
        if(!member.validatePassword(passwordEncoder, loginReqDto.getPassword())){
            throw new GlobalException(ErrorCode.BAD_PASSWORD);
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUsername());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberUsername(loginReqDto.getUsername());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getUsername());
            refreshTokenRepository.save(newToken);
        }
        setHeader(response, tokenDto);

        return ResponseEntity.ok().body(ResponseDto.success(
                MemberResponseDto.builder()
                        .username(member.getUsername())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        ));
    }
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());

    }
}
