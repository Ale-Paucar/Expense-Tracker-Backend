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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    //mapper to dto
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
    //get user
    private User getUser(ExpenseReqDTO expenseReqDTO){
        return userRepository.findById(expenseReqDTO.getUserId()).orElseThrow(() -> new NotFoundException("User with id " + expenseReqDTO.getUserId() + " not found"));
    }
    //get category
    private Category getCategory(ExpenseReqDTO expenseReqDTO){
        return categoryRepository.findById(expenseReqDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Category with id " + expenseReqDTO.getCategoryId() + " not found"));
    }
    //
    private Expense getExpense(Long expenseId){
        return expensesRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense with id " + expenseId + " not found"));
    }
    public ExpenseResDTO getExpenseFromId(Long id) {
        Expense expense = getExpense(id);
        return toExpenseResDTO(expense);
    }
    ///////
    public ExpenseResDTO addNewExpense(ExpenseReqDTO expenseReqDTO) {
        Category category = getCategory(expenseReqDTO);
        User user = getUser(expenseReqDTO);
        Expense expense = new Expense(
                expenseReqDTO.getDescription(),
                expenseReqDTO.getAmount(),
                category,
                user
        );
        expensesRepository.save(expense);
        return toExpenseResDTO(expense);
    }

    public ExpenseResDTO updateExistingExpense(Long id, ExpenseReqDTO expenseReqDTO) {
        Expense expense = getExpense(id);
        Category category = getCategory(expenseReqDTO);
        User user = getUser(expenseReqDTO);
        expense.setDescription(expenseReqDTO.getDescription());
        expense.setAmount(expenseReqDTO.getAmount());
        expense.setCategory(category);
        expense.setUser(user);
        expensesRepository.save(expense);
        return toExpenseResDTO(expense);
    }

    public void deleteExpenseFromId(Long id) {
        Expense expense = getExpense(id);
        expensesRepository.delete(expense);
    }

    public List<ExpenseResDTO> getAllExpenses(Integer month, Integer year, Long categoryId, Long userId) {
        int y = (year != null) ? year : LocalDateTime.now().getYear();
        LocalDateTime start;
        LocalDateTime end;
        if (month != null) {

            start = LocalDate.of(y, month, 1).atStartOfDay();
            end   = start.plusMonths(1);
        } else {

            start = LocalDate.of(y, 1, 1).atStartOfDay();
            end   = start.plusYears(1);
        }

        return expensesRepository
                .findByRangeAndCategory(userId, start, end, categoryId)
                .stream()
                .map(ex -> toExpenseResDTO(ex))
                .toList();
    }
}
