package com.kt.shopping.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kt.shopping.domain.history.History;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
}
