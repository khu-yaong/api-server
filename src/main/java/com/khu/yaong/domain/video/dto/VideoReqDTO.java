package com.khu.yaong.domain.video.dto;

import com.khu.yaong.domain.member.domain.Member;
import com.khu.yaong.domain.video.domain.View;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class VideoReqDTO {

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class LambdaRequestDTO {

        private final Long memberId;
        private final List<String> videoIds;

        public static LambdaRequestDTO toDTO(Member member) {
            return LambdaRequestDTO.builder()
                    .memberId(member.getId())
                    .videoIds(member.getViews().stream()
                            .map(View::getVideoId)
                            .toList())
                    .build();
        }
    }
}
