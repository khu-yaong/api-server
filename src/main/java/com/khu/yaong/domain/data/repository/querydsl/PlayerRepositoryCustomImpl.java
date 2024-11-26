package com.khu.yaong.domain.data.repository.querydsl;

import com.khu.yaong.domain.data.domain.Player;
import com.khu.yaong.domain.data.domain.QPlayer;
import com.khu.yaong.domain.member.domain.Team;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PlayerRepositoryCustomImpl implements PlayerRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Player> findBaseballPlayers(Integer pageSize, Long cursorId, String cursorName, Team team, String query) {
        QPlayer player = QPlayer.player;
        BooleanBuilder whereClause = getWhereClause(player, cursorId, cursorName, team, query);
        return queryFactory
                .selectFrom(player)
                .where(whereClause)
                .orderBy(player.name.asc(), player.id.asc())
                .limit(pageSize)
                .fetch();
    }

    private BooleanBuilder getWhereClause(QPlayer player, Long cursorId, String cursorName, Team team, String query) {
        BooleanBuilder whereClause = new BooleanBuilder();
        if (cursorId != null && cursorName != null) {
            whereClause.and(player.id.gt(cursorId)
                    .or(player.id.eq(cursorId).and(player.name.gt(cursorName))));
        }
        if (team != null) {
            whereClause.and(player.team.eq(team));
        }
        if (query != null) {
            whereClause.and(player.name.contains(query));
        }
        return whereClause;
    }
}
