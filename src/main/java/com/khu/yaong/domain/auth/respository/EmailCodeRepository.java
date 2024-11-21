package com.khu.yaong.domain.auth.respository;

import com.khu.yaong.domain.auth.domain.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailCodeRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByEmail(String email);

    void deleteByEmail(String email);

}
