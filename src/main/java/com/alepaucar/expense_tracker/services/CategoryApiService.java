package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.DTO.CategoryReqDTO;
import com.alepaucar.expense_tracker.DTO.CategoryResDTO;
import com.alepaucar.expense_tracker.exceptions.NotFoundException;
import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.CategoryType;
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
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        return new CategoryResDTO(category.getId(),category.getCategory(), category.getType().getValue());
    }

    public CategoryResDTO addNewCategory(CategoryReqDTO categoryReqDTO) {
        Category category = new Category(
                categoryReqDTO.category(),
                CategoryType.from(categoryReqDTO.type())
        );
        categoryRepository.save(category);
        return new CategoryResDTO(
                category.getId(),
                category.getCategory(),
                category.getType().getValue()
        );
    }

    public List<CategoryResDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResDTO(
                        c.getId(),
                        c.getCategory(),
                        c.getType().getValue()
                )).toList();
    }

    public CategoryResDTO updateExistingCategory(Long id, CategoryReqDTO categoryReqDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        category.setCategory(categoryReqDTO.category());
        category.setType(CategoryType.from(categoryReqDTO.type()));
        categoryRepository.save(category);
        return new CategoryResDTO(
                category.getId(),
                category.getCategory(),
                category.getType().getValue()
        );
    }
}
