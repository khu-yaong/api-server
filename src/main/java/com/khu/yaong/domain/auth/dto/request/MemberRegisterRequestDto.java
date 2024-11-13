package com.khu.yaong.domain.auth.dto.request;

import com.khu.yaong.domain.member.domain.MemberRole;
import com.khu.yaong.domain.member.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRegisterRequestDto {
    private String username;
    private String password;
    private String email;
    private MemberRole role;
    private Team team;
    private String profileImage;
}
