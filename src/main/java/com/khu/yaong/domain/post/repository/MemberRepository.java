package com.khu.yaong.domain.post.repository;

import com.khu.yaong.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
