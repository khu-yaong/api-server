package com.khu.yaong.domain.diary.domain;

import com.khu.yaong.domain.common.BaseTime;
import com.khu.yaong.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id; // 일지 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;    // 경기 관람일

    @Column(name = "match_details")
    private String matchDetails;    // 경기 관람내용

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "home_team", nullable = false)
    private String team1;

    @Column(name = "away_team", nullable = false)
    private String team2;

    @Column(name = "home_score", nullable = false)
    private int team1Score;

    @Column(name = "away_score", nullable = false)
    private int team2Score;

    @Column(name = "seat")
    private String seat;

    @Column(name = "content")
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    public void updateDiary(LocalDate matchDate, String place, String team1, int team1Score,
                            String team2, int team2Score, String seat, String content, String imageUrl) {
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

    public void setMatchDetails(String matchDetails) {
        this.matchDetails = matchDetails;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
