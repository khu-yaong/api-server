package com.khu.yaong.domain.diary.dto;

import com.khu.yaong.domain.diary.domain.Diary;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class DiaryResponseDto {
    private Long id;
    private Long memberId;
    private LocalDate matchDate;
    private String place;
    private String team1;
    private int team1Score;
    private String team2;
    private int team2Score;
    private String seat;
    private String content;
    private String imageUrl;

    @Builder
    public DiaryResponseDto(Long id, Long memberId, LocalDate matchDate, String place, String team1, int team1Score, String team2, int team2Score, String seat, String content, String imageUrl) {
        this.id = id;
        this.memberId = memberId;
        this.matchDate = matchDate;
        this.place = place;
        this.team1 = team1;
        this.team1Score = team1Score;
        this.team2 = team2;
        this.team2Score = team2Score;
        this.seat = seat;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    // Diary 엔티티를 기반으로 DiaryResponseDto 생성하는 생성자
    public DiaryResponseDto(Diary diary) {
        this.id = diary.getId();
        this.memberId = diary.getMember().getId();
        this.matchDate = diary.getMatchDate();
        this.place = diary.getPlace();
        this.team1 = diary.getTeam1();
        this.team1Score = diary.getTeam1Score();
        this.team2 = diary.getTeam2();
        this.team2Score = diary.getTeam2Score();
        this.seat = diary.getSeat();
        this.content = diary.getContent();
        this.imageUrl = diary.getImageUrl();
    }
}
