package com.khu.yaong.domain.diary.repository;

import com.khu.yaong.domain.diary.domain.Diary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByMemberId(Long memberId);

    @Query("SELECT d FROM Diary d WHERE d.matchDate = :matchDate")
    Optional<Diary> findByMatchDate(LocalDate matchDate);
//
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Override
//    public Optional<Diary> findByMatchDate(LocalDate matchDate) {
//
//        // JPQL 쿼리 작성
//        String jpql = "SELECT d FROM Diary d WHERE d.matchDate = :matchDate";
//
//        // 쿼리 실행
//        TypedQuery<Diary> query = entityManager.createQuery(jpql, Diary.class);
//        query.setParameter("matchDate", matchDate);
//
//        // 결과 반환
//        return query.getResultStream().findFirst();
//    }
}
