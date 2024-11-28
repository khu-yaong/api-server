package com.khu.yaong.domain.data.repository.querydsl;

import com.khu.yaong.domain.data.domain.Player;
import com.khu.yaong.domain.member.domain.Team;

import java.util.List;

public interface PlayerRepositoryCustom {

    List<Player> findBaseballPlayers(Integer pageSize, Long cursorId, String cursorName, Team team, String query);
}
