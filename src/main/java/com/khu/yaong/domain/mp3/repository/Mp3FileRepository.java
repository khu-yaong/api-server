package com.khu.yaong.domain.mp3.repository;

import com.khu.yaong.domain.mp3.domain.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mp3FileRepository extends JpaRepository<Mp3File, Long> {

    boolean existsByUrl(String url);
}
