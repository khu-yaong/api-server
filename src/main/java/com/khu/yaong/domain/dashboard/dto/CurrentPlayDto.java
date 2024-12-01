package com.khu.yaong.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class CurrentPlayDto {
    private String batter;
    private String pitcher;
    private String catcher;
    private int ballCount;
    private int strikeCount;
    private int outCount;
    private Map<String, String> baseRunners;
}
