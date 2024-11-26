package com.khu.yaong.domain.data.controller;

import com.khu.yaong.domain.data.dto.PlayerResDTO;
import com.khu.yaong.domain.data.service.PlayerService;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.data.DataSuccessCode;
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
