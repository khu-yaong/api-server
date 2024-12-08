package com.khu.yaong.domain.dashboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khu.yaong.domain.dashboard.dto.GameInfoDto;
import com.khu.yaong.domain.dashboard.exception.CrawlingErrorCode;
import com.khu.yaong.domain.dashboard.exception.CrawlingException;
import com.khu.yaong.domain.dashboard.exception.CrawlingSuccessCode;
import com.khu.yaong.domain.dashboard.service.AspxPageFetcher;
import com.khu.yaong.domain.dashboard.service.AspxPageParser;
import com.khu.yaong.global.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class AspxPageController {

    private final AspxPageFetcher aspxPageFetcher;
    private final AspxPageParser aspxPageParser;
    private final ObjectMapper objectMapper;

    public AspxPageController(AspxPageFetcher aspxPageFetcher, AspxPageParser aspxPageParser, ObjectMapper objectMapper) {
        this.aspxPageFetcher = aspxPageFetcher;
        this.aspxPageParser = aspxPageParser;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/fetch-aspx")
    public ResponseEntity<ApiResponse<?>> fetchAspxContent(String id) throws JsonProcessingException {
        if (id == null || id.isEmpty()) {
            throw new CrawlingException(CrawlingErrorCode.INVALID_URL);
        }
        String url = String.format(
                "https://www.koreabaseball.com/Game/LiveText.aspx?leagueId=1&seriesId=0&gameId=%s&gyear=2024",
                id
        );
        String htmlContent1 = aspxPageFetcher.fetchPageContent(url);
        // HTML 파싱
        GameInfoDto parsedData = aspxPageParser.parseHtmlToJson(htmlContent1);
        /*String jsonOutput = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parsedData);
        log.info("json Output :"+ jsonOutput);*/
        return ResponseEntity.ok(ApiResponse.success(CrawlingSuccessCode.PARSE_SUCCESS, parsedData));
    }
}