package com.khu.yaong.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class GameInfoDto {
    private String date;
    private String inning;
    private String scoreStatus;
    private String groundInfo;
    private TeamInfoDto teams;
    private CurrentPlayDto currentPlay;
    private LiveBroadcastDto liveBroadcast;
    private List<OtherGameDto> otherGames;
}
