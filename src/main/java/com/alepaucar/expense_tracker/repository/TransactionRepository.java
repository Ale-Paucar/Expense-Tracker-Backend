package com.alepaucar.expense_tracker.repository;

import com.alepaucar.expense_tracker.model.Transaction;

import com.alepaucar.expense_tracker.model.TransactionType;
import com.alepaucar.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByUser(User user);

    @Query("""
      SELECT t FROM Transaction t
      WHERE t.user.id = :userId
        AND t.createdAt >= :start
        AND t.createdAt <  :end
        AND (:categoryId IS NULL OR t.category.id = :categoryId)
        AND (:type IS NULL or t.type = :type)
      ORDER BY t.createdAt DESC
    """)
    List<Transaction> findAllTransactions(
            Long userId,
            LocalDateTime start,
            LocalDateTime end,
            Long categoryId,
            TransactionType type
    );
}
