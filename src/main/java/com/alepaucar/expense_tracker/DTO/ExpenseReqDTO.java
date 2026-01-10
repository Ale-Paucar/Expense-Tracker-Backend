package com.alepaucar.expense_tracker.DTO;

public record ExpenseReqDTO (
        String description,
        float  amount,
        Long categoryId,
        Long userId
){ }

