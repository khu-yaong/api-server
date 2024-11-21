package com.khu.yaong.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class MemberEmailVerifyRequestDto {
    private String email;
    private String code;
}
