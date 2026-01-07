package com.alepaucar.expense_tracker.DTO;

import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseResDTO {
    private Long id;
    private String description;
    private float  amount;
    private Category category;
    private User user;
    private LocalDateTime createdAt;
}
