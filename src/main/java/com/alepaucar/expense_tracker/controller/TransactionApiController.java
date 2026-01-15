package com.alepaucar.expense_tracker.controller;

import com.alepaucar.expense_tracker.DTO.TransactionReqDTO;
import com.alepaucar.expense_tracker.DTO.TransactionResDTO;
import com.alepaucar.expense_tracker.DTO.UserResDTO;
import com.alepaucar.expense_tracker.services.TransactionApiService;
import com.alepaucar.expense_tracker.services.UserApiService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
public class TransactionApiController {
    private final TransactionApiService transactionApiService;
    private final UserApiService userApiService;

    public TransactionApiController(TransactionApiService transactionApiService, UserApiService userApiService) {
        this.transactionApiService = transactionApiService;
        this.userApiService = userApiService;
    }

    /*-------------------------Transactions-------------------------*/
    //Get transaction
    @GetMapping("/transaction/{id}")
    public ResponseEntity<TransactionResDTO> getTransaction(@PathVariable Long id){
        TransactionResDTO expense = transactionApiService.getTransactionFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(expense);
    }
    //Get all transactions, it can filter by month or category or both
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

    //Add transaction
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResDTO>  createTransaction(@RequestBody TransactionReqDTO transactionReqDTO){
        TransactionResDTO res = transactionApiService.addNewTransaction(transactionReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    //Update transaction
    @PutMapping("/transaction/{id}")
    public ResponseEntity<TransactionResDTO>  updateTransaction(@PathVariable Long id, @RequestBody TransactionReqDTO transactionReqDTO){
        TransactionResDTO res = transactionApiService.updateExistingTransaction(id, transactionReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
    //Delete transaction
    @DeleteMapping("/transaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionApiService.deleteTransactionFromId(id);
        return ResponseEntity.noContent().build();
    }
    /*-------------------------Report-------------------------*/
    @GetMapping("/transaction/report.csv")
    public void getTransactionReport(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = true) Long userId,
            @RequestParam(required = false) String transactionType,
                HttpServletResponse response
    ) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        UserResDTO user = userApiService.getUserFromId(userId);
        List<TransactionResDTO> transactions = transactionApiService.getAllTransactions(month, year, categoryId, userId, transactionType);
        //if the username contains unusual characters or spaces...
        String safeUsername = user.username()
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", "-")
                .replaceAll("[^a-z0-9\\-]", "");

        String csvFileName = "user-" + safeUsername + "-report.csv";
        // Response header and content type
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + csvFileName + "\"");
        response.setContentType("text/csv");
        // csv parse
        StatefulBeanToCsv<TransactionResDTO> csvWriter = new StatefulBeanToCsvBuilder<TransactionResDTO>(response.getWriter())
                                                                                                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                                                                                                    .withOrderedResults(true)
                                                                                                    .build();
        csvWriter.write(transactions);
    }
}
