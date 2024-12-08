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
            String prompt = "야구와 관련된 4지 선다형 퀴즈 10개를 생성해줘. 각 퀴즈는 질문, 정답, 그리고 3개의 오답으로 구성되어야 해. 응답할 때 ## 야구 퀴즈 10문제\\n\\n 이런 제목 없이 응답만 해줘. 응답 형식은 항상 일정하게 다음과 같이 해줘." +
                    "1. 야구에서 투수가 던지는 공의 속도를 측정하는 기구는 무엇인가?\n" +
                    "정답: 레이더건\n" +
                    "오답: 스피도미터, 타코미터, 속도계\n" +
                    "\n" +
                    "2. 야구 경기에서 한 이닝에 3아웃을 기록하면 무슨 상황이 발생하는가?\n" +
                    "정답: 이닝 종료\n" +
                    "오답: 경기 종료, 게임 재개, 투수 교체\n" +
                    "\n" +
                    "3. 야구에서 홈런을 칠 때, 타자는 몇 개의 베이스를 모두 밟아야 하는가?\n" +
                    "정답: 4개\n" +
                    "오답: 3개, 2개, 1개\n" +
                    "\n" +
                    "4. 야구 경기에서 심판이 사용하는 신호 도구는 무엇인가?\n" +
                    "정답: 손짓과 깃발\n" +
                    "오답: 호각, 휘슬, 경적\n" +
                    "\n" +
                    "5. 야구에서 타자가 스트라이크 세 개를 당하면 어떻게 되는가?\n" +
                    "정답: 삼진 아웃\n" +
                    "오답: 볼넷, 파울, 안타\n" +
                    "\n" +
                    "6. 야구 경기에서 타석에 들어서는 선수는 어떤 역할을 하는가?\n" +
                    "정답: 타자\n" +
                    "오답: 투수, 포수, 주자\n" +
                    "\n" +
                    "7. 야구에서 1루수의 위치는 어디인가?\n" +
                    "정답: 1루 베이스 옆\n" +
                    "오답: 2루 베이스 옆, 3루 베이스 옆, 홈 베이스 옆\n" +
                    "\n" +
                    "8. 야구에서 한 경기의 이닝 수는 일반적으로 몇 이닝인가?\n" +
                    "정답: 9이닝\n" +
                    "오답: 7이닝, 5이닝, 10이닝\n" +
                    "\n" +
                    "9. 야구에서 투수가 던지는 공이 타자의 방망이에 맞지 않고 포수의 미트에 들어가는 것을 무엇이라고 하는가?\n" +
                    "정답: 헛스윙\n" +
                    "오답: 파울, 볼, 안타\n" +
                    "\n" +
                    "10. 야구에서 타자가 타구를 친 후 1루까지 안전하게 도달하는 것을 무엇이라고 하는가?\n" +
                    "정답: 1루타\n" +
                    "오답: 2루타, 3루타, 홈런";
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
