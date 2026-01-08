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
      WHERE e.createdAt >= :start
        AND e.createdAt <  :end
        AND (:categoryId IS NULL OR e.category.id = :categoryId)
    """)
    List<Expense> findByRangeAndCategory(
            LocalDateTime start,
            LocalDateTime end,
            Long categoryId
    );
}
