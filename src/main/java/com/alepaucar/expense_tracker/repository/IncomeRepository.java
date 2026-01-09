package com.alepaucar.expense_tracker.repository;

import com.alepaucar.expense_tracker.model.Income;
import com.alepaucar.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income,Long> {
    List<Income> findByUser(User user);

    @Query("""
      SELECT i FROM Income i
      WHERE i.user.id = :userId
        AND i.createdAt >= :start
        AND i.createdAt <  :end
        AND (:categoryId IS NULL OR i.category.id = :categoryId)
      ORDER BY i.createdAt DESC
    """)
    List<Income> findByUserRangeAndCategory(
            Long userId,
            LocalDateTime start,
            LocalDateTime end,
            Long categoryId
    );
}
