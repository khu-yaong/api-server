package com.khu.yaong.domain.mp3.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Mp3File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date uploadedDate = new java.util.Date();

    public Mp3File(String url, String fileName, String teamName, String category, String name) {
        this.url = url;
        this.fileName = fileName;
        this.teamName = teamName;
        this.category = category;
        this.name = name;
    }
}
