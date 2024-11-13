package com.khu.yaong.domain.member.repository;

import com.khu.yaong.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<Member, Long> {
}
