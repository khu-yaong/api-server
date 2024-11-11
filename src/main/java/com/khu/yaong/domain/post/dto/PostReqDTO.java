package com.khu.yaong.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostReqDTO {

    @Getter
    @RequiredArgsConstructor
    public static class PostDTO {
        private final String title;
        private final String content;
        private final String imageUrl;
    }
}
