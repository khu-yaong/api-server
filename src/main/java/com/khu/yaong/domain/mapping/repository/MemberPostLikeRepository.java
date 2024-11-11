package com.khu.yaong.domain.mapping.repository;

import com.khu.yaong.domain.mapping.domain.MemberPostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberPostLikeRepository extends JpaRepository<MemberPostLike, Long> {

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    Optional<MemberPostLike> findByMemberIdAndPostId(Long id, Long postId);

}
