package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.model.Transaction;

import com.alepaucar.expense_tracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportApiService {
    private final TransactionRepository transactionRepository;

    public ReportApiService( TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    public void getReport(Long id){

        List<Transaction> expens = transactionRepository.findAll();


    }
}
