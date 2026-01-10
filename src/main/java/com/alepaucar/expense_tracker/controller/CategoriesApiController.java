package com.alepaucar.expense_tracker.controller;

import com.alepaucar.expense_tracker.DTO.CategoryReqDTO;
import com.alepaucar.expense_tracker.DTO.CategoryResDTO;
import com.alepaucar.expense_tracker.services.CategoryApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoriesApiController {
    private final CategoryApiService categoryApiService;

    public CategoriesApiController(CategoryApiService categoryApiService) {
        this.categoryApiService = categoryApiService;
    }

    /*----------Categories----------*/
    //Get Category from id
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResDTO> getCategory(@PathVariable Long id){
        CategoryResDTO ctgry = categoryApiService.getCategoryFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ctgry);
    }
    //create a new category
    @PostMapping("/categories")
    public ResponseEntity<CategoryResDTO> postNewCategory(@RequestBody CategoryReqDTO categoryReqDTO) {
        CategoryResDTO ctgry = categoryApiService.addNewCategory(categoryReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ctgry);
    }
    //get all categories
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResDTO>> getAllCategories(){
        List<CategoryResDTO> allCategories = categoryApiService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(allCategories);
    }
    //
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryReqDTO categoryReqDTO) {
        CategoryResDTO ctgry = categoryApiService.updateExistingCategory(id, categoryReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ctgry);
    }
}
