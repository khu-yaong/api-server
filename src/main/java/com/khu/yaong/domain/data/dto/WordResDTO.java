package com.khu.yaong.domain.data.dto;

import com.khu.yaong.domain.data.domain.BaseballWord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class WordResDTO {

    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class WordDescriptionDTO {
        private final String word;
        private final String description;

        public static WordDescriptionDTO toDTO(BaseballWord word) {
            return WordDescriptionDTO.builder()
                    .word(word.getWord())
                    .description(word.getDescription())
                    .build();
        }
    }
}
