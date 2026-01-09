package com.alepaucar.expense_tracker.controller;

import com.alepaucar.expense_tracker.DTO.ExpenseReqDTO;
import com.alepaucar.expense_tracker.DTO.ExpenseResDTO;
import com.alepaucar.expense_tracker.services.ExpenseApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseApiController {
    private final ExpenseApiService expenseApiService;

    public ExpenseApiController(ExpenseApiService expenseApiService) {
        this.expenseApiService = expenseApiService;
    }

    /*----------Expenses----------*/
    //Get expense
    @GetMapping("/expense/{id}")
    public ResponseEntity<ExpenseResDTO> getExpense(@PathVariable Long id){
        ExpenseResDTO expense = expenseApiService.getExpenseFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(expense);
    }
    //Get all expenses, it can filter by month or category or both
    @GetMapping("/expense")
    public ResponseEntity<List<ExpenseResDTO>> getAllExpenses(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = true) Long userId
    ){
        List<ExpenseResDTO> expenses = expenseApiService.getAllExpenses(month, year, categoryId, userId);
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
}
