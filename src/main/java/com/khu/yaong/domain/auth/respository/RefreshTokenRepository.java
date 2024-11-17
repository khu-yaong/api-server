package com.khu.yaong.domain.auth.respository;

import com.khu.yaong.domain.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByMemberId(Long memberId);
}
