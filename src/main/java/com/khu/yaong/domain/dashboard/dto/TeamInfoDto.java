package com.khu.yaong.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class TeamInfoDto {
    private TeamDto awayTeam;
    private TeamDto homeTeam;
}
