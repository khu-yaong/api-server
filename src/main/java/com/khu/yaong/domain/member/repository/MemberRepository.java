package com.khu.yaong.domain.member.repository;

import com.khu.yaong.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    Optional<Member> findById(Long memberId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
