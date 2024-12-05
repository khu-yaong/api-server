package com.khu.yaong.domain.video.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.domain.video.domain.View;
import lombok.*;

import java.util.List;

@Getter
public class VideoResDTO {

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class UpdatedViewDTO {

        private final String member;
        private final String videoId;

        public static UpdatedViewDTO toDTO(View view) {
            return UpdatedViewDTO.builder()
                    .member(view.getMember().getUsername())
                    .videoId(view.getVideoId())
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class RecommendationDTO {
        private final List<RecommendedVideoDTO> recommendVideos;
        public static RecommendationDTO toDTO(RecommendationMapDTO mapDTO) {
            return RecommendationDTO.builder()
                    .recommendVideos(mapDTO.getRecommendVideos().stream()
                            .map(RecommendedVideoDTO::toDTO)
                            .toList())
                    .build();
        }
    }

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class RecommendedVideoDTO {
        private final Team team;
        private final String videoId;
        private final String title;
        private final String thumbnail;

        public static RecommendedVideoDTO toDTO(RecommendedVideoMapDTO mapDTO) {
            return RecommendedVideoDTO.builder()
                    .team(Team.toEnum(mapDTO.getTeam()))
                    .videoId(mapDTO.getVideoId())
                    .title(mapDTO.getTitle())
                    .thumbnail(mapDTO.getThumbnail())
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RecommendationMapDTO {
        @JsonProperty("recommended_videos")
        private List<RecommendedVideoMapDTO> recommendVideos;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RecommendedVideoMapDTO {
        private String team;
        private String videoId;
        private String title;
        private String thumbnail;
    }
}
