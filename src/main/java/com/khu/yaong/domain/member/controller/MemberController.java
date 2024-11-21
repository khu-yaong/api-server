package com.khu.yaong.domain.member.controller;

import com.khu.yaong.domain.auth.service.AuthService;
import com.khu.yaong.domain.member.dto.response.MemberProfileResponseDto;
import com.khu.yaong.domain.member.exception.MemberSuccessCode;
import com.khu.yaong.domain.member.service.MemberService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;

    /*// 회원정보 가져오는 메서드
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<MemberInfoResponseDto>> getMemberInfo() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        log.info("MemberId : {}", memberId);
        MemberInfoResponseDto memberInfo = memberService.getMemberInfo(memberId);
        log.info("Member Info: {}", memberInfo);
        return ResponseEntity.ok(ApiResponse.success(MemberSuccessCode.INFO_SUCCESS, memberInfo));
    }*/
    // 회원정보 조회
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<MemberProfileResponseDto>> getMemberProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String authenticatedMemberId = userDetails.getUsername();  // 인증된 사용자 ID 가져오기
        MemberProfileResponseDto profile = memberService.getMemberProfileById(Long.valueOf(authenticatedMemberId));
        return ResponseEntity.ok(ApiResponse.success(MemberSuccessCode.INFO_SUCCESS, profile));
    }


    // 회원정보 수정

    // 회원탈퇴
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdrawMember(@RequestHeader("Authorization") String accessToken) {
        String token = accessToken.replace("Bearer ", "");
        authService.withdraw(token);
        return ResponseEntity.ok(ApiResponse.success(MemberSuccessCode.WITHDRAW_SUCCESS));
    }
}
