package com.khu.yaong.domain.auth.service;

import com.khu.yaong.domain.auth.domain.RefreshToken;
import com.khu.yaong.domain.auth.respository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    // 리프레시 토큰 저장
    public void saveRefreshToken(Long memberId, String token, long expiration){
        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(memberId)
                .orElse(new RefreshToken());
        refreshToken.setMemberId(memberId);
        refreshToken.setToken(token);
        refreshToken.setExpiration(LocalDateTime.now().plusMinutes(expiration));

        refreshTokenRepository.save(refreshToken);
    }

    // 리프레시 토큰 검증
    public boolean isRefreshTokenValid(String token){
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        return refreshToken.isPresent()&& refreshToken.get().getExpiration().isAfter(LocalDateTime.now());
    }
    // 리프레시 토큰 삭제
    public void deleteRefreshToken(Long memberId){
        refreshTokenRepository.findByMemberId(memberId).ifPresent(refreshTokenRepository::delete);
    }
}
