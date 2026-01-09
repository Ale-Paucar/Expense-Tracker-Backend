package com.alepaucar.expense_tracker.repository;

import com.alepaucar.expense_tracker.model.Expense;

import com.alepaucar.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpensesRepository extends JpaRepository<Expense,Long> {
    List<Expense> findByUser(User user);

    @Query("""
      SELECT e FROM Expense e
      WHERE e.user.id = :userId
        AND e.createdAt >= :start
        AND e.createdAt <  :end
        AND (:categoryId IS NULL OR e.category.id = :categoryId)
      ORDER BY e.createdAt DESC
    """)
    List<Expense> findByUserRangeAndCategory(
            Long userId,
            LocalDateTime start,
            LocalDateTime end,
            Long categoryI
    );
}
