package com.alepaucar.expense_tracker.controller;

import com.alepaucar.expense_tracker.DTO.TransactionReqDTO;
import com.alepaucar.expense_tracker.DTO.TransactionResDTO;
import com.alepaucar.expense_tracker.services.TransactionApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionApiController {
    private final TransactionApiService transactionApiService;

    public TransactionApiController(TransactionApiService transactionApiService) {
        this.transactionApiService = transactionApiService;
    }

    /*----------Expenses----------*/
    //Get expense
    @GetMapping("/transaction/{id}")
    public ResponseEntity<TransactionResDTO> getTransaction(@PathVariable Long id){
        TransactionResDTO expense = transactionApiService.getTransactionFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(expense);
    }
    //Get all expenses, it can filter by month or category or both
    @GetMapping("/transaction")
    public ResponseEntity<List<TransactionResDTO>> getAllTransactions(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = true) Long userId,
            @RequestParam(required = false) String transactionType
    ){
        List<TransactionResDTO> expenses = transactionApiService.getAllTransactions(month, year, categoryId, userId, transactionType);
        return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }

    //Add expense
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResDTO>  createTransaction(@RequestBody TransactionReqDTO transactionReqDTO){
        TransactionResDTO res = transactionApiService.addNewTransaction(transactionReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    //Update expense
    @PutMapping("/transaction/{id}")
    public ResponseEntity<TransactionResDTO>  updateTransaction(@PathVariable Long id, @RequestBody TransactionReqDTO transactionReqDTO){
        TransactionResDTO res = transactionApiService.updateExistingTransaction(id, transactionReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    //Delete expense
    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionApiService.deleteTransactionFromId(id);
        return ResponseEntity.noContent().build();
    }
}
