package com.khu.yaong.domain.data.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Getter
public class PlayerReqDTO {

    @Getter
    @RequiredArgsConstructor
    public static class PitcherModDTO {

        // rank
        private final Integer eraRank;
        private final Integer winRank;
        private final Integer svRank;
        private final Integer hldRank;
        private final Integer soRank;
        private final Integer whipRank;

        // record
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

        public static List<Field> getAllFields() {
            Class<PitcherModDTO> clazz = PitcherModDTO.class;
            Field[] fields = clazz.getDeclaredFields();
            return Arrays.stream(fields).toList();
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class FielderModDTO {

        // rank
        private final Integer opsRank;
        private final Integer rRank;
        private final Integer rbiRank;
        private final Integer hrRank;
        private final Integer avgRank;
        private final Integer hRank;

        // record
        private final Double avg;
        private final Integer hr;
        private final Integer h;
        private final Integer r;
        private final Integer rbi;
        private final Integer sb;
        private final Double obp;
        private final Double ops;

        public static List<Field> getAllFields() {
            Class<FielderModDTO> clazz = FielderModDTO.class;
            Field[] fields = clazz.getDeclaredFields();
            return Arrays.stream(fields).toList();
        }
    }
}
