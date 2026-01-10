package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.model.Expense;
import com.alepaucar.expense_tracker.model.Income;
import com.alepaucar.expense_tracker.repository.ExpensesRepository;
import com.alepaucar.expense_tracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportApiService {
    private final IncomeRepository incomeRepository;
    private final ExpensesRepository expensesRepository;

    public ReportApiService(IncomeRepository incomeRepository, ExpensesRepository expensesRepository) {
        this.incomeRepository = incomeRepository;
        this.expensesRepository = expensesRepository;
    }

    public void getReport(Long id){
        List<Income> incomes = incomeRepository.findAll();
        List<Expense> expenses = expensesRepository.findAll();

        List<TransactionDTO> transactions = List.of(incomes,expenses);
    }
}
