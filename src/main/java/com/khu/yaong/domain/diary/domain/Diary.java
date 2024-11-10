package com.khu.yaong.domain.diary.domain;

import com.khu.yaong.domain.common.BaseTime;
import com.khu.yaong.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
