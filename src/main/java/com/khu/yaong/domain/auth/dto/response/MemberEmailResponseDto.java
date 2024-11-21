package com.khu.yaong.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberEmailResponseDto {
    private String email;
    private String message;

}
