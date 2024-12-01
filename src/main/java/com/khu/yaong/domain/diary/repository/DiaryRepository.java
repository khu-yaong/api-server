package com.khu.yaong.domain.diary.repository;

import com.khu.yaong.domain.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> , DiaryRepositoryCustom{
    List<Diary> findAllByMemberId(Long memberId);
}
