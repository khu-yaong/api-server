package com.khu.yaong.domain.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DiaryRequestDto {
    private LocalDate matchDate;
    private String place;
    private String team1;
    private int team1Score;
    private String team2;
    private int team2Score;
    private String seat;
    private String content;
    private String imageUrl;
}
