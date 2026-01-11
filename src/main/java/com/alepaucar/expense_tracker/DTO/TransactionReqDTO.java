package com.alepaucar.expense_tracker.DTO;

public record   TransactionReqDTO(
        String description,
        float  amount,
        Long categoryId,
        Long userId,
        String type
){ }

