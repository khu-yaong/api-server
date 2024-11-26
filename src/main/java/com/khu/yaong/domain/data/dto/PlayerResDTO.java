package com.khu.yaong.domain.data.dto;

import com.khu.yaong.domain.data.domain.Player;
import com.khu.yaong.domain.member.domain.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
public class PlayerResDTO {

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class PlayerInfoDTO {

        private final Long playerId;
        private final Team team;
        private final String name;
        private final Integer no;
        private final String position;
        private final LocalDate birth;
        private final String hwSpec;
        private final Double avg;       // 타율
        private final Double ops;       // OPS
        private final Double era;       // 평균자책점
        private final String ip;        // 이닝

        public static PlayerInfoDTO toDTO(Player player) {

            Double avg = null;
            Double ops = null;
            Double era = null;
            String ip = null;

            if (player.getFielder() != null ) {
                avg = player.getFielder().getAvg();
                ops = player.getFielder().getOps();
            }
            if (player.getPitcher() != null ) {
                era = player.getPitcher().getEra();
                ip = player.getPitcher().getIp();
            }

            return PlayerInfoDTO.builder()
                    .playerId(player.getId())
                    .team(player.getTeam())
                    .name(player.getName())
                    .no(player.getNo())
                    .position(player.getPosition())
                    .birth(player.getBirth())
                    .hwSpec(player.getHwSpec())
                    .avg(avg)
                    .ops(ops)
                    .era(era)
                    .ip(ip)
                    .build();
        }
    }
}
