package com.khu.yaong.domain.data.controller;

import com.khu.yaong.domain.data.dto.WordResDTO;
import com.khu.yaong.domain.data.service.WordService;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.data.WordSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Word API", description = "야구 용어 사전 API")
public class WordController {

    private final WordService wordService;

    @Operation(summary = "[구현완료] 야구 용어 목록 조회", description = """
    ## 야구 용어 목록을 조회합니다.
    * pageSize : 페이지 사이즈 (1 이상)
    * cursorWord(nullable) : 이전 페이지의 가장 마지막 야구 용어
    * query(nullable) : 입력한 검색어 (검색어가 없으면 가나다순으로 야구 용어 목록 조회)
    """)
    @GetMapping("/words")
    ApiResponse<List<WordResDTO.WordDescriptionDTO>> getBaseballWords(
            @RequestParam Integer pageSize,
            @RequestParam(required = false) String cursorWord,
            @RequestParam(required = false) String query) {
        List<WordResDTO.WordDescriptionDTO> wordDescriptionDTOS = wordService.getBaseballWords(pageSize, cursorWord, query);
        return ApiResponse.success(WordSuccessCode.WORD_LIST_FOUND, wordDescriptionDTOS);
    }
}
