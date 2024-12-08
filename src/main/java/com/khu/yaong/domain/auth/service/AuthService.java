package com.khu.yaong.domain.auth.service;
import com.khu.yaong.domain.auth.domain.EmailVerification;
import com.khu.yaong.domain.auth.dto.response.*;
import com.khu.yaong.domain.auth.exception.AuthErrorCode;
import com.khu.yaong.domain.auth.exception.AuthException;
import com.khu.yaong.domain.auth.respository.EmailCodeRepository;
import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.auth.dto.request.MemberLoginRequestDto;
import com.khu.yaong.domain.auth.dto.request.MemberRegisterRequestDto;
import com.khu.yaong.domain.member.domain.MemberRole;
import com.khu.yaong.domain.member.repository.MemberRepository;
import com.khu.yaong.global.s3.S3ImageService;
import com.khu.yaong.global.security.jwt.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final EmailCodeRepository emailCodeRepository;

    private final S3ImageService s3ImageService;

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional
    public MemberRegisterResponseDto register(MemberRegisterRequestDto request, String imageUrl) {
        if (existsByEmail(request.getEmail())) {
            s3ImageService.deleteImageFromS3(imageUrl);
            throw new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (existsByUsername(request.getUsername())) {
            s3ImageService.deleteImageFromS3(imageUrl);
            throw new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(request.getRole())
                .team(request.getTeam())
                .profileImage(imageUrl)
                .build();
        Member savedMember = memberRepository.save(member);
        return new MemberRegisterResponseDto(savedMember.getId(),savedMember.getCreatedDate());
    }

    public MemberLoginResponseDto login(MemberLoginRequestDto request) {
        Optional<Member> member = memberRepository.findByUsername(request.getUsername());

        if (member.isPresent() && member.get().getPassword().equals(request.getPassword())) {
            String accessToken = jwtTokenProvider.createAccessToken(member.get().getId(),member.get().getRole());
            String refreshToken = jwtTokenProvider.createRefreshToken(member.get().getId(),member.get().getRole());

            refreshTokenService.saveRefreshToken(member.get().getId(),refreshToken,jwtTokenProvider.getRefreshTokenExpiration());
            return new MemberLoginResponseDto(accessToken,refreshToken,member.get().getId(),member.get().getUsername(),member.get().getRole(),member.get().getProfileImage());

        }
        else {
            throw new AuthException(AuthErrorCode.MEMBER_NOT_FOUND);

        }
    }

    public String refreshAccessToken(String refreshToken){
        if (!refreshTokenService.isRefreshTokenValid(refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        return jwtTokenProvider.renewAccessToken(refreshToken);
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
    public Member createMemberByGoogle(GoogleUserInfo googleUserInfo) {

        return Member.builder()
                .username(googleUserInfo.getName())
                .email(googleUserInfo.getEmail())
                .password(null)
                .role(MemberRole.USER)
                .team(null)
                .profileImage(null)
                .build();
    }

    @Transactional
    public void withdraw(String accessToken){
        Long memberId = jwtTokenProvider.getMemberIdFromToken(accessToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.MEMBER_NOT_FOUND));
        memberRepository.deleteById(memberId);
        refreshTokenService.deleteRefreshToken(memberId);

    }

    public MemberEmailResponseDto sendCodeToEmail(String email) {
        String code = generateVerificationCode();
        emailService.sendCode(email, "[YAONG] 이메일 인증코드", "인증코드 : " + code);
        saveVerificationCode(email, code);

        return new MemberEmailResponseDto(email, "인증코드가 발송되었습니다.");
    }

    @Transactional
    public MemberEmailVerifyResponseDto verifyEmailCode(String email, String code) {
        Optional<EmailVerification> emailVerification = emailCodeRepository.findByEmail(email);
        String savedCode = emailVerification.isPresent() ? emailVerification.get().getCode() : null;

        if (savedCode == null) {
            throw new AuthException(AuthErrorCode.EMAIL_CODE_NOT_FOUND);
        }

        if (!savedCode.equals(code)) {
            throw new AuthException(AuthErrorCode.EMAIL_CODE_MISMATCH);
        }
        /*if (isCodeExpired(email)) {
            throw new AuthException(AuthErrorCode.EMAIL_CODE_EXPIRED);
        }*/
        emailCodeRepository.deleteByEmail(email);
        return new MemberEmailVerifyResponseDto(email, true);

    }

    private String generateVerificationCode() {
        return String.valueOf((int) ((Math.random() * 900000) + 100000));   // 6자리 랜덤 숫자
    }

    public void saveVerificationCode(String email, String code){
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setEmail(email);
        emailVerification.setCode(code);
        emailVerification.setExpirationTime(LocalDateTime.now());
        emailCodeRepository.save(emailVerification);
        System.out.println(emailVerification);
        System.out.println(emailCodeRepository.findByEmail(email));
    }
}
