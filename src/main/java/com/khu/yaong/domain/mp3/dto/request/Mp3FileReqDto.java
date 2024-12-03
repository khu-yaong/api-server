package com.khu.yaong.domain.mp3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Mp3FileReqDto {
    private String fileName;
    private String teamName;
    private String category;
    private String name;
}
