package com.khu.yaong.domain.data.domain;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;

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

    @Column
    private Integer opsRank;

    @Column
    private Integer rRank;

    @Column
    private Integer rbiRank;

    @Column
    private Integer hrRank;

    @Column
    private Integer avgRank;

    @Column
    private Integer hRank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public void setData(Field field, String value) {
        String fieldName = field.getName();
        if (field.getType() == Integer.class) {
            int intValue = Integer.parseInt(value);
            switch(fieldName) {
                case ("opsRank") -> this.opsRank = intValue;
                case ("rRank") -> this.rRank = intValue;
                case ("rbiRank") -> this.rbiRank = intValue;
                case ("hrRank") -> this.hrRank = intValue;
                case ("avgRank") -> this.avgRank = intValue;
                case ("hRank") -> this.hRank = intValue;
                case ("hr") -> this.hr = intValue;
                case ("h") -> this.h = intValue;
                case ("r") -> this.r = intValue;
                case ("rbi") -> this.rbi = intValue;
                case("sb") -> this.sb = intValue;
            }
        } else if (field.getType() == Double.class) {
            double doubleValue = Double.parseDouble(value);
            switch(fieldName) {
                case ("avg") -> this.avg = doubleValue;
                case ("obp") -> this.obp = doubleValue;
                case ("ops") -> this.ops = doubleValue;
            }
        }
    }
}
