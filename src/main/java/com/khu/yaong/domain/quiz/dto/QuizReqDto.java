package com.khu.yaong.domain.quiz.dto;

import io.swagger.v3.oas.annotations.media.Content;
import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class QuizReqDto {

    private List<Content> contents;


}
