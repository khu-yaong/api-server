package com.khu.yaong.domain.video.repository;

import com.khu.yaong.domain.video.domain.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ViewRepository extends JpaRepository<View, Long> {

    Optional<View> findByMemberIdAndVideoId(Long memberId, String videoId);
}
