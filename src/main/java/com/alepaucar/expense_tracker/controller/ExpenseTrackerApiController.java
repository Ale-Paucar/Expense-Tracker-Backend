package com.alepaucar.expense_tracker.controller;

import com.alepaucar.expense_tracker.DTO.*;
import com.alepaucar.expense_tracker.services.CategoryApiService;
import com.alepaucar.expense_tracker.services.ExpenseApiService;
import com.alepaucar.expense_tracker.services.IncomeApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExpenseTrackerApiController {
    private final IncomeApiService incomeApiService;
    private final ExpenseApiService expenseApiService;
    private final CategoryApiService categoryApiService;

    public ExpenseTrackerApiController(IncomeApiService incomeApiService, ExpenseApiService expenseApiService, CategoryApiService categoryApiService) {
        this.incomeApiService = incomeApiService;
        this.expenseApiService = expenseApiService;
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
    /*----------Expenses----------*/
    //Get expense
    @GetMapping("/expense/{id}")
    public ResponseEntity<ExpenseResDTO>  getExpense(@PathVariable Long id){
        ExpenseResDTO expense = expenseApiService.getExpenseFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(expense);
    }
    //Get all expenses, it can filter by month or category or both
    @GetMapping("/expense")
    public ResponseEntity<List<ExpenseResDTO>> getAllExpenses(
        @RequestParam(required = false) Integer month,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) Long categoryId
    ){
        List<ExpenseResDTO> expenses = expenseApiService.getAllExpenses(month, year, categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }

    //Add expense
    @PostMapping("/expense")
    public ResponseEntity<ExpenseResDTO>  createExpense(@RequestBody ExpenseReqDTO expenseReqDTO){
        ExpenseResDTO res = expenseApiService.addNewExpense(expenseReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    //Update expense
    @PutMapping("/expense/{id}")
    public ResponseEntity<ExpenseResDTO>  updateExpense(@PathVariable Long id, @RequestBody ExpenseReqDTO expenseReqDTO){
        ExpenseResDTO res = expenseApiService.updateExistingExpense(id,expenseReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    //Delete expense
    @DeleteMapping("/expense/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
        expenseApiService.deleteExpenseFromId(id);
        return ResponseEntity.noContent().build();
    }

    /*----------Income----------*/
    //Get income
    @GetMapping("/income/{id}")
    public ResponseEntity<IncomeResDTO>  getIncome(@PathVariable Long id){
        IncomeResDTO income = incomeApiService.getIncomeFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(income);
    }
    //Get all incomes, it can filter by month or category or both
    @GetMapping("/income")
    public List<IncomeResDTO> getAllIncomes(
            @RequestParam(required = false) String Month,
            @RequestParam(required = false) Long category
    ){
        return new ArrayList<>();
    }

    //Add income
    @PostMapping("/income")
    public  ResponseEntity<IncomeResDTO> createIncome(@RequestBody IncomeReqDTO incomeReqDTO){
        IncomeResDTO income = incomeApiService.addNewIncome(incomeReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(income);
    }
    //Update income
    @PutMapping("/income/{id}")
    public ResponseEntity<IncomeResDTO> updateIncome(@PathVariable Long id, @RequestBody IncomeReqDTO incomeReqDTO){
        IncomeResDTO income = incomeApiService.updateExistingIncome(id,incomeReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(income);
    }
    //Delete income
    @DeleteMapping("/income/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id){
        expenseApiService.deleteExpenseFromId(id);
        return ResponseEntity.noContent().build();
    }
    /*-----Reports-----*/
    @PostMapping("/budget")
    public void createBudget(){

    }
    @GetMapping("/budget")
    public void getBudget(){

    }
    @GetMapping("/report")
    public void getReport(){
        
    }
}
