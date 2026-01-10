package com.alepaucar.expense_tracker.DTO;

import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.User;
import java.time.LocalDateTime;

public record IncomeResDTO (
    Long id,
    String description,
    float  amount,
    Category category,
    User user,
    LocalDateTime createdAt
){}
