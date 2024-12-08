package com.khu.yaong.domain.mp3.dto.response;

import com.khu.yaong.domain.mp3.domain.Mp3File;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Mp3FileResDto {
    private String fileName;
    private String url;
    private String teamName;
    private String category;
    private String name;

    public Mp3FileResDto(Mp3File mp3File) {
        this.fileName = mp3File.getFileName();
        this.url = mp3File.getUrl();
        this.teamName = mp3File.getTeamName();
        this.category = mp3File.getCategory();
        this.name = mp3File.getName();
    }
}
