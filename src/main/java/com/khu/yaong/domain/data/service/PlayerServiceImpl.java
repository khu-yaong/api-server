package com.khu.yaong.domain.data.service;

import com.khu.yaong.domain.data.dto.PlayerResDTO;
import com.khu.yaong.domain.data.repository.PlayerRepository;
import com.khu.yaong.domain.member.domain.Team;
import com.khu.yaong.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public List<PlayerResDTO.PlayerInfoDTO> getPlayers(Integer pageSize, Long cursorId, String cursorName, Team team, String query) {

        // Authorization
        SecurityUtil.getCurrentMemberId();

        return playerRepository.findBaseballPlayers(pageSize, cursorId, cursorName, team, query).stream()
                .map(PlayerResDTO.PlayerInfoDTO::toDTO)
                .toList();
    }
}
