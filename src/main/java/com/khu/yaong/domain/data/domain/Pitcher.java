package com.khu.yaong.domain.data.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Pitcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column
    private Double era;      // 평균자책점

    @Column
    private Integer win;     // 승

    @Column
    private Integer lose;    //  패

    @Column
    private Integer sv;      // 세이브

    @Column
    private Integer hld;     // 홀드

    @Column
    private String ip;       // 이닝

    @Column
    private Integer so;      // 삼진

    @Column
    private Integer ha;      // 피안타

    @Column
    private Integer hra;     // 피홈런

    @Column
    private Integer bb;      // 볼넷

    @Column
    private Double whip;     // whip

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}
