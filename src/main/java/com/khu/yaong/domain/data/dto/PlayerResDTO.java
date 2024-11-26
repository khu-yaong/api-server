package com.khu.yaong.domain.data.dto;

import com.khu.yaong.domain.data.domain.Fielder;
import com.khu.yaong.domain.data.domain.Pitcher;
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

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class PlayerDetailDTO {
        private final Long playerId;
        private final Team team;
        private final String name;
        private final Integer no;
        private final String position;
        private final LocalDate birth;
        private final String hwSpec;
        private final PitcherDTO pitcherRecord;
        private final FielderDTO fielderRecord;

        public static PlayerDetailDTO toDTO(Player player) {
            return PlayerDetailDTO.builder()
                    .playerId(player.getId())
                    .team(player.getTeam())
                    .name(player.getName())
                    .no(player.getNo())
                    .position(player.getPosition())
                    .birth(player.getBirth())
                    .hwSpec(player.getHwSpec())
                    .pitcherRecord(PitcherDTO.toDTO(player.getPitcher()))
                    .fielderRecord(FielderDTO.toDTO(player.getFielder()))
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    private static class PitcherDTO {

        private final Double era;
        private final Integer win;
        private final Integer lose;
        private final Integer sv;
        private final Integer hld;
        private final String ip;
        private final Integer so;
        private final Integer ha;
        private final Integer hra;
        private final Integer bb;
        private final Double whip;

        public static PitcherDTO toDTO(Pitcher pitcher) {
            if (pitcher == null) {
                return null;
            } else {
                return PitcherDTO.builder()
                        .era(pitcher.getEra())
                        .win(pitcher.getWin())
                        .lose(pitcher.getLose())
                        .sv(pitcher.getSv())
                        .hld(pitcher.getHld())
                        .ip(pitcher.getIp())
                        .so(pitcher.getSo())
                        .ha(pitcher.getHa())
                        .hra(pitcher.getHra())
                        .bb(pitcher.getBb())
                        .whip(pitcher.getWhip())
                        .build();
            }
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    private static class FielderDTO {

        private final Double avg;
        private final Integer hr;
        private final Integer h;
        private final Integer r;
        private final Integer rbi;
        private final Integer sb;
        private final Double obp;
        private final Double ops;

        public static FielderDTO toDTO(Fielder fielder) {
            if (fielder == null) {
                return null;
            } else {
                return FielderDTO.builder()
                        .avg(fielder.getAvg())
                        .hr(fielder.getHr())
                        .h(fielder.getH())
                        .r(fielder.getR())
                        .rbi(fielder.getRbi())
                        .sb(fielder.getSb())
                        .obp(fielder.getObp())
                        .ops(fielder.getOps())
                        .build();
            }
        }
    }
}
