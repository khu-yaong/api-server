package com.khu.yaong.domain.auth.service;

import com.khu.yaong.domain.auth.exception.AuthErrorCode;
import com.khu.yaong.domain.auth.exception.AuthException;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.auth.dto.request.MemberLoginRequestDto;
import com.khu.yaong.domain.auth.dto.request.MemberRegisterRequestDto;
import com.khu.yaong.domain.auth.dto.response.MemberLoginResponseDto;
import com.khu.yaong.domain.auth.dto.response.MemberRegisterResponseDto;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    public MemberRegisterResponseDto register(MemberRegisterRequestDto request) {
        if (existsByEmail(request.getEmail())) {
            throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (existsByUsername(request.getUsername())) {
            throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
        }
        Member member = Member.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .team(request.getTeam())
                .profileImage(request.getProfileImage())
                .build();
        Member savedMember = memberRepository.save(member);
        return new MemberRegisterResponseDto(savedMember.getId(),savedMember.getCreatedDate());
    }

    public MemberLoginResponseDto login(MemberLoginRequestDto request) {
        Optional<Member> member = memberRepository.findByUsername(request.getUsername());

        if (member.isPresent() && member.get().getPassword().equals(request.getPassword())) {
            String token = jwtTokenProvider.createToken(member.get().getId(),member.get().getRole());
            return new MemberLoginResponseDto(token, member.get().getId(), member.get().getUsername(), member.get().getRole(), member.get().getProfileImage());
        }
        else {
            throw new AuthException(AuthErrorCode.MEMBER_NOT_FOUND);

        }
    }


    public Member createMemberByKakao(KakaoUserInfo kakaoUserInfo) {

        return Member.builder()
                .username(kakaoUserInfo.getNickname())
                .email(kakaoUserInfo.getEmail())
                .password(null)
                .role(MemberRole.USER)
                .team(null)
                .profileImage(null)
                .build();
    }
}
