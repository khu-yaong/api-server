package com.khu.yaong.domain.quiz.controller;

import com.khu.yaong.domain.quiz.dto.QuizResDto;
import com.khu.yaong.domain.quiz.service.QuizService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/")
    public ApiResponse<List<Map<String, Object>>> getQuizList() {
        try {
            return ApiResponse.success(SuccessCode.SUCCESS,quizService.generateQuizzes());
        } catch (Exception e){
            throw new RuntimeException("퀴즈 생성 중 오류가 발생했습니다.");
        }

    }
}
