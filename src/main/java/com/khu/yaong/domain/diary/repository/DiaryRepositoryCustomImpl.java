package com.khu.yaong.domain.diary.repository;

import com.khu.yaong.domain.diary.domain.Diary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.Optional;

public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Optional<Diary> findByMatchDate(LocalDate matchDate) {

        // JPQL 쿼리 작성
        String jpql = "SELECT d FROM Diary d WHERE d.matchDate = :matchDate";

        // 쿼리 실행
        TypedQuery<Diary> query = entityManager.createQuery(jpql, Diary.class);
        query.setParameter("matchDate", matchDate);

        // 결과 반환
        return query.getResultStream().findFirst();
    }
}
