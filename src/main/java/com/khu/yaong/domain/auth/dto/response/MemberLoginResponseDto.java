package com.khu.yaong.domain.auth.dto.response;

import com.khu.yaong.domain.member.domain.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberLoginResponseDto {
    private String token;
    private Long userId;
    private String username;
    private MemberRole role;
    private String profileImage;
}
