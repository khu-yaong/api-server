package com.khu.yaong.domain.member.dto.response;

import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.post.dto.PostResDTO;
import lombok.Builder;
import lombok.Getter;
import java.util.List;


@Getter
@Builder
public class MemberProfileResponseDto {
    private String username;
    private Team team;
    private String profileImageUrl;
    private int level;
    private int experience;
    private int nextLevelExp;
    private List<PostResDTO.PostInfoDTO> postList;
}