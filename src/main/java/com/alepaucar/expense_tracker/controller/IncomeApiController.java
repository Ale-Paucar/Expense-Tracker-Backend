package com.alepaucar.expense_tracker.controller;

import com.alepaucar.expense_tracker.DTO.IncomeReqDTO;
import com.alepaucar.expense_tracker.DTO.IncomeResDTO;
import com.alepaucar.expense_tracker.services.IncomeApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IncomeApiController {
    private final IncomeApiService incomeApiService;

    public IncomeApiController(IncomeApiService incomeApiService) {
        this.incomeApiService = incomeApiService;
    }
    /*----------Income----------*/
    //Get income
    @GetMapping("/income/{id}")
    public ResponseEntity<IncomeResDTO> getIncome(@PathVariable Long id){
        IncomeResDTO income = incomeApiService.getIncomeFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(income);
    }
    //Get all incomes, it can filter by month or category or both
    @GetMapping("/income")
    public ResponseEntity<List<IncomeResDTO>> getAllIncomes(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = true) Long userId
    ){
        List<IncomeResDTO> incomes = incomeApiService.getAllIncomes(month,year,categoryId,userId);
        return ResponseEntity.status(HttpStatus.OK).body(incomes);
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
        incomeApiService.deleteIncomeFromId(id);
        return ResponseEntity.noContent().build();
    }
}
