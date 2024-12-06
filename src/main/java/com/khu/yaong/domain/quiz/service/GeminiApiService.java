package com.khu.yaong.domain.quiz.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GeminiApiService {

    private final Logger logger = Logger.getLogger(GeminiApiService.class.getName());

    @Value("${gemini.api-key}")
    private String apiKey;

    public String generateQuizzes() {
        try {
            // API URL 설정
            String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
            logger.info("Connecting to API: " + apiUrl);

            // HTTP 연결 생성
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // HTTP 요청 설정
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            logger.info("HTTP connection established. Sending request...");

            // 요청 본문 작성
            String prompt = "야구와 관련된 4지 선다형 퀴즈 10개를 생성해줘. 각 퀴즈는 질문, 정답, 그리고 3개의 오답으로 구성되어야 해. 응답할 때 ## 야구 퀴즈 10문제\\n\\n 이런 제목 없이 응답만 해줘";
            String requestBody = "{"
                    + "  \"contents\": ["
                    + "    {"
                    + "      \"parts\": [{"
                    + "        \"text\": \"" + prompt + "\""
                    + "      }]"
                    + "    }"
                    + "  ]"
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
                logger.info("Request body sent successfully: " + requestBody);
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            logger.info("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 응답 데이터 읽기
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    logger.info("Response received: " + response);

                    // JSON 응답 파싱
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonNode = mapper.readTree(response.toString());

                    // "candidates" 필드 추출
                    String quizzes = "";
                    if (jsonNode.has("candidates")) {
                        for (JsonNode candidate : jsonNode.get("candidates")) {
                            JsonNode parts = candidate.path("content").path("parts");
                            for (JsonNode part : parts) {
                                quizzes = part.path("text").asText();
                                logger.info("Extracted quiz text: " + quizzes);
                            }
                        }
                    } else {
                        logger.warning("Response does not contain 'candidates' field.");
                        throw new RuntimeException("Gemini API 응답에 'candidates' 필드가 없습니다.");
                    }

                    return quizzes;
                }
            } else {
                logger.severe("Failed to call Gemini API. HTTP Response Code: " + responseCode);
                throw new RuntimeException("Gemini API 호출 실패: HTTP " + responseCode);
            }
        } catch (Exception e) {
            logger.severe("Error occurred while calling Gemini API: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Gemini API 호출 중 오류 발생", e);
        }
    }
}
