package com.khu.yaong.domain.member.controller;

import com.khu.yaong.domain.member.dto.MemberProfileResponseDto;
import com.khu.yaong.domain.member.exception.MemberNotFoundException;
import com.khu.yaong.domain.member.service.MemberProfileService;
import com.khu.yaong.global.auth.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    public MemberProfileController(MemberProfileService memberProfileService) {
        this.memberProfileService = memberProfileService;
    }

    @GetMapping("/me")
    public ResponseEntity<MemberProfileResponseDto> getMemberProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long authenticatedMemberId = userDetails.getId();  // 인증된 사용자 ID 가져오기
            MemberProfileResponseDto profile = memberProfileService.getMemberProfile(authenticatedMemberId);
            return ResponseEntity.ok(profile);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
