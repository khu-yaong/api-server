package com.khu.yaong.domain.data.repository;

import com.khu.yaong.domain.data.domain.Pitcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PitcherRepository extends JpaRepository<Pitcher, Long> {

    Optional<Pitcher> findByPlayerId(Long playerId);
}
