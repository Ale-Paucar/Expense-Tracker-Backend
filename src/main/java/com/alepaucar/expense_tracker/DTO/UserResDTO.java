package com.alepaucar.expense_tracker.DTO;


import java.time.LocalDateTime;

public record UserResDTO (
    Long id,
    String username,
    String email,
    String firstName,
    String lastName,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){}
