package com.khu.yaong.domain.data.service;

import com.khu.yaong.domain.data.dto.PlayerReqDTO;

public interface PlayerRedisService {

    void requestPitcherMod(Long pitcherId, PlayerReqDTO.PitcherModDTO pitcherModDTO);
}
