package com.khu.yaong.domain.member.controller;

import com.khu.yaong.domain.member.dto.response.MemberInfoResponseDto;
import com.khu.yaong.domain.member.exception.MemberSuccessCode;
import com.khu.yaong.domain.member.service.MemberService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.security.jwt.JwtTokenProvider;
import com.khu.yaong.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원정보 가져오는 메서드
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<MemberInfoResponseDto>> getMemberInfo() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        log.info("MemberId : {}", memberId);
        MemberInfoResponseDto memberInfo = memberService.getMemberInfo(memberId);
        log.info("Member Info: {}", memberInfo);
        return ResponseEntity.ok(ApiResponse.success(MemberSuccessCode.INFO_SUCCESS, memberInfo));
    }
}
