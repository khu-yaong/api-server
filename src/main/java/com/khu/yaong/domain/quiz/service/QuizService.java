package com.khu.yaong.domain.quiz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.quiz.domain.Quiz;
import com.khu.yaong.domain.quiz.dto.QuizResDto;
import com.khu.yaong.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final GeminiApiService geminiApiService;

    public List<Map<String, Object>> generateQuizzes() {
        String quizList = geminiApiService.generateQuizzes();
        List<Map<String, Object>> quizzes = new ArrayList<>();
        try {
            // 응답 텍스트에서 질문 블록 추출
            String[] questions = quizList.split("\\n\\n"); // 빈 줄 기준으로 블록 분리
            for (String questionBlock : questions) {
                String[] lines = questionBlock.split("\\n"); // 블록 내부에서 줄 단위로 분리
                if (lines.length < 2) continue; // 질문과 답변이 모두 포함된 블록만 처리

                Map<String, Object> quiz = new HashMap<>();

                // 질문 추출
                String question = lines[0].replaceFirst("\\d+\\. ", "").trim();
                quiz.put("question", question);

                // 정답 및 오답 추출
                String correctAnswer = "";
                List<String> wrongAnswers = new ArrayList<>();
                for (int i = 1; i < lines.length; i++) {
                    if (lines[i].startsWith("정답:")) {
                        correctAnswer = lines[i].replace("정답:", "").trim();
                    } else if (lines[i].startsWith("오답:")) {
                        String[] wrongs = lines[i].replace("오답:", "").trim().split(", ");
                        for (String wrong : wrongs) {
                            wrongAnswers.add(wrong.trim());
                        }
                    }
                }

                // 결과 저장
                quiz.put("correctAnswer", correctAnswer);
                quiz.put("wrongAnswers", wrongAnswers);
                quizzes.add(quiz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("퀴즈 응답 처리 중 오류 발생", e);
        }
        return quizzes;
    }
}