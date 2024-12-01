package com.khu.yaong.domain.diary.repository;

import com.khu.yaong.domain.diary.domain.Diary;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepositoryCustom {

    public Optional<Diary> findByMatchDate(LocalDate matchDate);
}
