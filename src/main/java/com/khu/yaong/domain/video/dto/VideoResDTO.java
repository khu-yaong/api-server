package com.khu.yaong.domain.video.dto;

import com.khu.yaong.domain.video.domain.View;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
