package com.khu.yaong.domain.data.controller;

import com.khu.yaong.domain.data.dto.PlayerReqDTO;
import com.khu.yaong.domain.data.dto.PlayerResDTO;
import com.khu.yaong.domain.data.service.PlayerService;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.global.common.response.ApiResponse;
import com.khu.yaong.global.common.response.data.DataSuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "[구현완료] 야구 선수 정보 조회", description = """
    ## 야구 선수 정보를 조회합니다.
    ### Return Data : 선수 기본 정보, pitcherRecord(야수면 null), fielderRecord(투수면 null)
    * playerId : 선수 아이디 (not pitcherId, fielderId)
    """)
    @GetMapping("/players/{playerId}")
    ApiResponse<PlayerResDTO.PlayerDetailDTO> getPlayerInfo(@RequestParam Long playerId) {
        PlayerResDTO.PlayerDetailDTO playerDetailDTO = playerService.getPlayerInfo(playerId);
        return ApiResponse.success(DataSuccessCode.PLAYER_FOUND, playerDetailDTO);
    }

    @Operation(summary = "[구현완료] 투수 정보 수정 요청", description = """
    ## 투수 정보 수정을 요청합니다.
    * 수정 요청 정보를 redis에 저장합니다. 동일한 수정 요청이 10회 이상이면 DB에 반영하고 redis key를 삭제합니다.
    * 동일한 회원이 10분 내에 전송한 동일한 수정 요청은 횟수에 포함되지 않습니다.
    * 해당 선수가 투수가 아니면 자동으로 예외 처리됩니다.
    * playerId : 선수 아이디 (not pitcherId, fielderId)
    """)
    @PatchMapping("/players/{playerId}/pitchers")
    ApiResponse<Void> requestPitcherMod(
            @PathVariable Long playerId,
            @RequestBody PlayerReqDTO.PitcherModDTO pitcherModDTO
    ) {
        playerService.requestPitcherMod(playerId, pitcherModDTO);
        return ApiResponse.success(DataSuccessCode.PITCHER_INFO_MOD_REQUESTED);
    }

    @Operation(summary = "[구현완료] 야수 정보 수정 요청", description = """
    ## 야수 정보 수정을 요청합니다.
    * 수정 요청 정보를 redis에 저장합니다. 동일한 수정 요청이 10회 이상이면 DB에 반영하고 redis key를 삭제합니다.
    * 동일한 회원이 10분 내에 전송한 동일한 수정 요청은 횟수에 포함되지 않습니다.
    * 해당 선수가 야수가 아니면 자동으로 예외 처리됩니다.
    * playerId : 선수 아이디 (not pitcherId, fielderId)
    """)
    @PatchMapping("/players/{playerId}/fielders")
    ApiResponse<Void> requestFielderMod(
            @PathVariable Long playerId,
            @RequestBody PlayerReqDTO.FielderModDTO fielderModDTO
    ) {
        playerService.requestFielderMod(playerId, fielderModDTO);
        return ApiResponse.success(DataSuccessCode.FIELDER_INFO_MOD_REQUESTED);
    }
}
