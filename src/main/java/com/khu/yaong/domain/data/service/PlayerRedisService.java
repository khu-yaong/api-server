package com.khu.yaong.domain.data.service;

import com.khu.yaong.domain.data.dto.PlayerReqDTO;

public interface PlayerRedisService {

    void requestPitcherMod(Long playerId, PlayerReqDTO.PitcherModDTO pitcherModDTO);

    void requestFielderMod(Long playerId, PlayerReqDTO.FielderModDTO fielderModDTO);
}
