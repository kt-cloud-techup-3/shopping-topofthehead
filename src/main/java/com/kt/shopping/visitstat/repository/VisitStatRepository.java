package com.kt.shopping.visitstat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kt.shopping.visitstat.domain.VisitStat;

@Repository
public interface VisitStatRepository extends JpaRepository<VisitStat,Long> {
}
