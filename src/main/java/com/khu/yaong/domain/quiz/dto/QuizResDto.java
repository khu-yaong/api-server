package com.khu.yaong.domain.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class QuizResDto {
    private String question;
    private List<String> choices;
    private String answer;
}
