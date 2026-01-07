package com.alepaucar.expense_tracker.DTO;

import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpenseReqDTO {
    private String description;
    private float  amount;
    private Long categoryId;
    private Long userId;

}
