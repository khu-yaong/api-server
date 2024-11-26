package com.khu.yaong.domain.data.controller;

import com.khu.yaong.domain.data.dto.PlayerResDTO;
import com.khu.yaong.domain.data.service.PlayerService;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.data.DataSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dictionary API", description = "야구 사전 API")
public class PlayerController {

    private final PlayerService playerService;

    @Operation(summary = "[구현완료] 야구 선수 목록 조회", description = """
    ## 야구 선수 목록을 조회합니다.
    * pageSize : 페이지 사이즈 (1 이상)
    * cursorId(nullable) : 이전 페이지의 가장 마지막 야구 선수 id (정수값)
    * cursorName(nullable) : 이전 페이지의 가장 마지막 야구 선수 이름
    * team(nullable) : 구단명
    * query(nullable) : 입력한 검색어 (검색어가 없으면 가나다순으로 야구 선수 목록 조회)
    """)
    @GetMapping("/players")
    ApiResponse<List<PlayerResDTO.PlayerInfoDTO>> getPlayers(
            @RequestParam Integer pageSize,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) String cursorName,
            @RequestParam(required = false) Team team,
            @RequestParam(required = false) String query
    ) {
        List<PlayerResDTO.PlayerInfoDTO> playerInfoDTOS = playerService.getPlayers(pageSize, cursorId, cursorName, team, query);
        return ApiResponse.success(DataSuccessCode.PLAYER_LIST_FOUND, playerInfoDTOS);
    }
}
