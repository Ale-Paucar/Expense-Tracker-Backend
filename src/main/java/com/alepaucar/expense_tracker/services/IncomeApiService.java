package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;

@Service
public class IncomeApiService {
    private final IncomeRepository incomeRepository;

    public IncomeApiService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }


}
