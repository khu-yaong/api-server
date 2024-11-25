package com.khu.yaong.domain.data.repository;

import com.khu.yaong.domain.data.domain.BaseballWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseballWordRepository extends JpaRepository<BaseballWord, Long> {
}
