package com.alepaucar.expense_tracker.services;


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

    public IncomeResDTO getIncomeFromId(Long id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new NotFoundException("Income with id " + id+ " not found"));
        return toIncomeResDTO(income);
    }

    public IncomeResDTO addNewIncome(IncomeReqDTO incomeReqDTO) {
        Category category = categoryRepository.findById(incomeReqDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Category with id " + incomeReqDTO.getCategoryId() + " not found"));
        User user = userRepository.findById(incomeReqDTO.getUserId()).orElseThrow(() -> new NotFoundException("User with id " + incomeReqDTO.getUserId() + " not found"));
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
        Income income = incomeRepository.findById(id).orElseThrow(() -> new NotFoundException("Income with id " + id+ " not found"));
        Category category = categoryRepository.findById(incomeReqDTO.getCategoryId()).orElseThrow(() -> new NotFoundException("Category with id " + incomeReqDTO.getCategoryId() + " not found"));
        User user = userRepository.findById(incomeReqDTO.getUserId()).orElseThrow(() -> new NotFoundException("User with id " + incomeReqDTO.getUserId() + " not found"));
        income.setDescription(incomeReqDTO.getDescription());
        income.setAmount(incomeReqDTO.getAmount());
        income.setCategory(category);
        income.setUser(user);
        incomeRepository.save(income);
        return toIncomeResDTO(income);
    }

    public void deleteIncomeFromId(Long id) {
        Income income = incomeRepository.findById(id).orElseThrow(() -> new NotFoundException("Income with id " + id+ " not found"));
        incomeRepository.delete(income);
    }

}
