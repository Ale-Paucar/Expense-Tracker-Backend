package com.alepaucar.expense_tracker.DTO;

import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.TransactionType;
import com.alepaucar.expense_tracker.model.User;

import java.time.LocalDateTime;

public record TransactionResDTO(
        Long id,
        String description,
        float  amount,
        Category category,
        User user,
        TransactionType type,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){}
