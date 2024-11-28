package com.khu.yaong.domain.data.repository;

import com.khu.yaong.domain.data.domain.Player;
import com.khu.yaong.domain.data.repository.querydsl.PlayerRepositoryCustom;
import com.khu.yaong.domain.member.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, PlayerRepositoryCustom {
}
