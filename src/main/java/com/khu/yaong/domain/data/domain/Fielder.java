package com.khu.yaong.domain.data.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Fielder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column
    private Double avg;     // 타율

    @Column
    private Integer hr;      // 홈런

    @Column
    private Integer h;       // 안타

    @Column
    private Integer r;       // 득점

    @Column
    private Integer rbi;     // 타점

    @Column
    private Integer sb;      // 도루

    @Column
    private Double obp;     // 출루율

    @Column
    private Double ops;     // ops

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
}
