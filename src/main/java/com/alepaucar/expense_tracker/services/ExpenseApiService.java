package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.DTO.ExpenseReqDTO;
import com.alepaucar.expense_tracker.DTO.ExpenseResDTO;
import com.alepaucar.expense_tracker.exceptions.NotFoundException;
import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.Expense;
import com.alepaucar.expense_tracker.model.User;
import com.alepaucar.expense_tracker.repository.CategoryRepository;
import com.alepaucar.expense_tracker.repository.ExpensesRepository;
import com.alepaucar.expense_tracker.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExpenseApiService {
    private final ExpensesRepository expensesRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ExpenseApiService(ExpensesRepository expensesRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.expensesRepository = expensesRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
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

    public ExpenseResDTO getExpenseFromId(Long id) {
        Expense expense = expensesRepository.findById(id).orElseThrow(() -> new NotFoundException("Expense with id " + id+ " not found"));
        return toExpenseResDTO(expense);
    }

    public ExpenseResDTO addNewExpense(ExpenseReqDTO expenseReqDTO) {
        Category category = categoryRepository.findById(expenseReqDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Category with id " + expenseReqDTO.getCategoryId() + " not found"));
            Expense expense = new Expense(
                    expenseReqDTO.getDescription(),
                    expenseReqDTO.getAmount(),
                    category
            );
            expensesRepository.save(expense);
            return toExpenseResDTO(expense);
    }

    public ExpenseResDTO updateExistingExpense(Long id, ExpenseReqDTO expenseReqDTO) {
        Expense expense = expensesRepository.findById(id).orElseThrow(() -> new NotFoundException("Expense with id " + id+ " not found"));
        Category category = categoryRepository.findById(expenseReqDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Category with id " + expenseReqDTO.getCategoryId() + " not found"));
        User user = userRepository.findById(expenseReqDTO.getUserId()).orElseThrow(() -> new NotFoundException("User with id " + expenseReqDTO.getUserId() + " not found"));
        expense.setDescription(expenseReqDTO.getDescription());
        expense.setAmount(expenseReqDTO.getAmount());
        expense.setCategory(category);
        expense.setUser(user);
        expensesRepository.save(expense);
        return toExpenseResDTO(expense);
    }

    public void deleteExpenseFromId(Long id) {
        Expense expense = expensesRepository.findById(id).orElseThrow(() -> new NotFoundException("Expense with id " + id+ " not found"));
        expensesRepository.delete(expense);
    }
}
