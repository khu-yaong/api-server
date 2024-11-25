package com.khu.yaong.domain.member.dto.request;

import com.khu.yaong.domain.member.domain.Team;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberProfileReqDto {
    private String username;
    private String email;
    private String password;
    private Team team;
    private String imageUrl;
}
