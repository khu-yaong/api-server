package com.khu.yaong.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberRegisterResponseDto {
    private Long userId;
    private LocalDateTime createdAt;
}
