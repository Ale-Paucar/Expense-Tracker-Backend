package com.alepaucar.expense_tracker.DTO;

public record UserReqDTO (
    String username,
    String email,
    String firstName,
    String lastName
){}
