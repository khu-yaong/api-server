package com.khu.yaong.domain.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER,   // 일반 사용자
    ADMIN   // 관리자

}
