package com.khu.yaong.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberEmailVerifyResponseDto {
    private String email;
    private boolean isVerified;
}
