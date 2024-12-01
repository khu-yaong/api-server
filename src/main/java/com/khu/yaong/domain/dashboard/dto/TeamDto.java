package com.khu.yaong.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TeamDto {
    private Map<String, Object> teamInfo;
    private List<Map<String, String>> batters;
    private List<Map<String, String>> pitchers;
}