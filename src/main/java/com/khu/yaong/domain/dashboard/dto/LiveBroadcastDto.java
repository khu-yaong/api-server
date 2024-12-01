package com.khu.yaong.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class LiveBroadcastDto {
    private String awayTeam;
    private String awayScore;
    private String homeTeam;
    private String homeScore;
    private List<List<String>> inningScores;
    private List<Map<String, String>> summaryStats;
}
