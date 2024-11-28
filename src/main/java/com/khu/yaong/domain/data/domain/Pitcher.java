package com.khu.yaong.domain.data.domain;

import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Field;

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

    @Column
    private Integer eraRank;

    @Column
    private Integer winRank;

    @Column
    private Integer svRank;

    @Column
    private Integer hldRank;

    @Column
    private Integer soRank;

    @Column
    private Integer whipRank;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    public void setData(Field field, String value) {
        String fieldName = field.getName();
        if (field.getType() == Integer.class) {
            int intValue = Integer.parseInt(value);
            switch(fieldName) {
                case ("win") -> this.win = intValue;
                case ("lose") -> this.lose = intValue;
                case ("sv") -> this.sv = intValue;
                case ("hld") -> this.hld = intValue;
                case ("so") -> this.so = intValue;
                case ("ha") -> this.ha = intValue;
                case ("hra") -> this.hra = intValue;
                case ("bb") -> this.bb = intValue;
                case("eraRank") -> this.eraRank = intValue;
                case ("winRank") -> this.winRank = intValue;
                case ("svRank") -> this.svRank = intValue;
                case ("hldRank") -> this.hldRank = intValue;
                case ("soRank") -> this.soRank = intValue;
                case ("whipRank") -> this.whipRank = intValue;
            }
        } else if (field.getType() == Double.class) {
            double doubleValue = Double.parseDouble(value);
            switch(fieldName) {
                case ("era") -> this.era = doubleValue;
                case ("whip") -> this.whip = doubleValue;
            }
        } else if (field.getType() == String.class) {
            if (fieldName.equals("ip")) {
                this.ip = value;
            }
        }
    }
}

