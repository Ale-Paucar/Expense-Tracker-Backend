package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.DTO.ExpenseReqDTO;
import com.alepaucar.expense_tracker.DTO.ExpenseResDTO;
import com.alepaucar.expense_tracker.exceptions.NotFoundException;
import com.alepaucar.expense_tracker.model.Expense;
import com.alepaucar.expense_tracker.repository.ExpensesRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExpenseApiService {
    private final ExpensesRepository expensesRepository;

    public ExpenseApiService(ExpensesRepository expensesRepository) {
        this.expensesRepository = expensesRepository;
    }


    public ExpenseResDTO getExpenseFromId(Long id) {
        Expense expense =  expensesRepository.findById(id).orElseThrow(() -> new NotFoundException("Not Found"));
        return toExpenseResDTO(expense);
    }

    public ExpenseResDTO addNewExpense(ExpenseReqDTO expenseReqDTO) {
            Expense expense = new Expense(
                    expenseReqDTO.getDescription(),
                    expenseReqDTO.getAmount(),
                    expenseReqDTO.getCategory()
            );
            expensesRepository.save(expense);
            return toExpenseResDTO(expense);
    }

    private ExpenseResDTO toExpenseResDTO (Expense ex){
        return new ExpenseResDTO(
                ex.getId(),
                ex.getDescription(),
                ex.getAmount(),
                ex.getCategory(),
                ex.getUser(),
                ex.getCreatedAt()
        );
    }


}
