package com.alepaucar.expense_tracker.services;


import com.alepaucar.expense_tracker.DTO.ExpenseReqDTO;
import com.alepaucar.expense_tracker.DTO.ExpenseResDTO;
import com.alepaucar.expense_tracker.DTO.IncomeReqDTO;
import com.alepaucar.expense_tracker.DTO.IncomeResDTO;
import com.alepaucar.expense_tracker.exceptions.NotFoundException;
import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.Expense;
import com.alepaucar.expense_tracker.model.Income;
import com.alepaucar.expense_tracker.model.User;
import com.alepaucar.expense_tracker.repository.CategoryRepository;
import com.alepaucar.expense_tracker.repository.IncomeRepository;
import com.alepaucar.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncomeApiService {
    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public IncomeApiService(IncomeRepository incomeRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }
    //mapper to DTO
    private IncomeResDTO toIncomeResDTO (Income in){
        return new IncomeResDTO(
                in.getId(),
                in.getDescription(),
                in.getAmount(),
                in.getCategory(),
                in.getUser(),
                in.getCreatedAt()
        );
    }
    //get user
    private User getUser(IncomeReqDTO incomeReqDTO){
        return userRepository.findById(incomeReqDTO.getUserId()).orElseThrow(() -> new NotFoundException("User with id " + incomeReqDTO.getUserId() + " not found"));
    }
    //get category
    private Category getCategory(IncomeReqDTO incomeReqDTO){
        return categoryRepository.findById(incomeReqDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Category with id " + incomeReqDTO.getCategoryId() + " not found"));
    }
    //get Expense
    private Income getIncome(Long expenseId){
        return incomeRepository.findById(expenseId).orElseThrow(() -> new NotFoundException("Income with id " + expenseId + " not found"));
    }

    /// ////////
    public IncomeResDTO getIncomeFromId(Long id) {
        Income income = getIncome(id);
        return toIncomeResDTO(income);
    }

    public IncomeResDTO addNewIncome(IncomeReqDTO incomeReqDTO) {
        Category category = getCategory(incomeReqDTO);
        User user = getUser(incomeReqDTO);
        Income income = new Income(
                incomeReqDTO.getDescription(),
                incomeReqDTO.getAmount(),
                category,
                user
        );
        incomeRepository.save(income);
        return toIncomeResDTO(income);
    }

    public IncomeResDTO updateExistingIncome(Long id, IncomeReqDTO incomeReqDTO) {
        Income income = getIncome(id);
        Category category = getCategory(incomeReqDTO);
        User user = getUser(incomeReqDTO);
        income.setDescription(incomeReqDTO.getDescription());
        income.setAmount(incomeReqDTO.getAmount());
        income.setCategory(category);
        income.setUser(user);
        incomeRepository.save(income);
        return toIncomeResDTO(income);
    }

    public void deleteIncomeFromId(Long id) {
        Income income = getIncome(id);
        incomeRepository.delete(income);
    }

    public List<IncomeResDTO> getAllIncomes(Integer month, Integer year, Long categoryId, Long userId) {
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

        return incomeRepository
                .findByUserRangeAndCategory(userId, start, end, categoryId)
                .stream()
                .map(in -> toIncomeResDTO(in))
                .toList();
    }
}
