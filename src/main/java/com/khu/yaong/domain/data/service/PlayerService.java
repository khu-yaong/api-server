package com.khu.yaong.domain.data.service;

import com.khu.yaong.domain.data.dto.PlayerReqDTO;
import com.khu.yaong.domain.data.dto.PlayerResDTO;
import com.khu.yaong.domain.member.domain.Team;

import java.util.List;

public interface PlayerService extends PlayerRedisService {

    List<PlayerResDTO.PlayerInfoDTO> getPlayers(Integer pageSize, Long cursorId, String cursorName, Team team, String query);

    PlayerResDTO.PlayerDetailDTO getPlayerInfo(Long playerId);

}
