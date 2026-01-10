package com.alepaucar.expense_tracker.DTO;

public record IncomeReqDTO (
    String description,
    float  amount,
    Long categoryId,
    Long userId
){}