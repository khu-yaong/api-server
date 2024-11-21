package com.khu.yaong.domain.member.dto.response;

import com.khu.yaong.domain.member.domain.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberProfileResponseDto {
    private String username;
    private Team team;
    private String profileImageUrl;
    private int level;
    private int experience;
    private int nextLevelExp;
}
