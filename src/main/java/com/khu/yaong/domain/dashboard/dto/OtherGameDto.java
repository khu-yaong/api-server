package com.khu.yaong.domain.dashboard.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OtherGameDto {
    private String ground;
    private String awayTeam;
    private String awayScore;
    private String homeTeam;
    private String homeScore;
    private String baseRunners;
    private String outs;
}