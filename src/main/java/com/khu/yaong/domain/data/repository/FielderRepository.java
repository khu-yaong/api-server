package com.khu.yaong.domain.data.repository;

import com.khu.yaong.domain.data.domain.Fielder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FielderRepository extends JpaRepository<Fielder, Long> {
}
