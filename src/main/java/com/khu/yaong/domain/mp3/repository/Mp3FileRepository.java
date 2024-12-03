package com.khu.yaong.domain.mp3.repository;

import com.khu.yaong.domain.mp3.domain.Mp3File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface Mp3FileRepository extends JpaRepository<Mp3File, Long> {

    boolean existsByUrl(String url);

    Optional<Mp3File> findByFileName(String filename);
    List<Mp3File> findByTeamNameAndCategory(String teamName, String category);
}
