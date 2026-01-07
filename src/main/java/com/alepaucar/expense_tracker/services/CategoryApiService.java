package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.DTO.CategoryReqDTO;
import com.alepaucar.expense_tracker.DTO.CategoryResDTO;
import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryApiService {
    private final CategoryRepository categoryRepository;

    public CategoryApiService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public CategoryResDTO getCategoryFromId(Long id) {
        Category category = categoryRepository.getById(id);
        return new CategoryResDTO(category.getId(),category.getCategory());
    }

    public CategoryResDTO addNewCategory(CategoryReqDTO categoryReqDTO) {
        Category category = new Category(categoryReqDTO.getCategory());
        categoryRepository.save(category);
        return new CategoryResDTO(category.getId(),category.getCategory());
    }

    public List<CategoryResDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResDTO(
                        c.getId(),
                        c.getCategory()
                )).toList();
    }
}
