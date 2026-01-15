package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.DTO.TransactionReqDTO;
import com.alepaucar.expense_tracker.DTO.TransactionResDTO;
import com.alepaucar.expense_tracker.exceptions.NotFoundException;
import com.alepaucar.expense_tracker.model.Category;
import com.alepaucar.expense_tracker.model.Transaction;
import com.alepaucar.expense_tracker.model.TransactionType;
import com.alepaucar.expense_tracker.model.User;
import com.alepaucar.expense_tracker.repository.CategoryRepository;
import com.alepaucar.expense_tracker.repository.TransactionRepository;
import com.alepaucar.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionApiService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TransactionApiService(TransactionRepository transactionRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }
    //mapper to dto
    private TransactionResDTO toTransactionResDTO(Transaction transaction){
        return new TransactionResDTO(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getUser(),
                transaction.getType(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
    //get user
    private User getUser(TransactionReqDTO transactionReqDTO){
        return userRepository.findById(transactionReqDTO.userId()).orElseThrow(() -> new NotFoundException("User with id " + transactionReqDTO.userId() + " not found"));
    }
    //get category
    private Category getCategory(TransactionReqDTO transactionReqDTO){
        return categoryRepository.findById(transactionReqDTO.categoryId()).orElseThrow(() -> new NotFoundException("Category with id " + transactionReqDTO.categoryId() + " not found"));
    }
    //get Expense
    private Transaction getTransaction(Long transactionId){
        return transactionRepository.findById(transactionId).orElseThrow(() -> new NotFoundException("Expense with id " + transactionId + " not found"));
    }
    // Service Methods
    public TransactionResDTO getTransactionFromId(Long id) {
        Transaction transaction = getTransaction(id);
        return toTransactionResDTO(transaction);
    }

    public TransactionResDTO addNewTransaction(TransactionReqDTO transactionReqDTO) {
        Category category = getCategory(transactionReqDTO);
        User user = getUser(transactionReqDTO);
        Transaction transaction = new Transaction(
                transactionReqDTO.description(),
                transactionReqDTO.amount(),
                category,
                user,
                TransactionType.from(transactionReqDTO.type())
        );
        transactionRepository.save(transaction);
        return toTransactionResDTO(transaction);
    }

    public TransactionResDTO updateExistingTransaction(Long id, TransactionReqDTO transactionReqDTO) {
        Transaction transaction = getTransaction(id);
        Category category = getCategory(transactionReqDTO);
        User user = getUser(transactionReqDTO);
        transaction.setDescription(transactionReqDTO.description());
        transaction.setAmount(transactionReqDTO.amount());
        transaction.setCategory(category);
        transaction.setUser(user);
        transaction.setType(TransactionType.from(transactionReqDTO.type()));
        transaction.setUpdatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);
        return toTransactionResDTO(transaction);
    }

    public void deleteTransactionFromId(Long id) {
        Transaction transaction = getTransaction(id);
        transactionRepository.delete(transaction);
    }

    public List<TransactionResDTO> getAllTransactions(Integer month, Integer year, Long categoryId, Long userId, String transactionType) {
         int y =  (year != null)  ? year : LocalDateTime.now().getYear();
        LocalDateTime start;
        LocalDateTime end;
        if (month != null) {
            if(month<1 || month > 12){
                throw new IllegalArgumentException("Month must be between 1 and 12");
            }else{
                start = LocalDate.of(y, month, 1).atStartOfDay();
                end   = start.plusMonths(1);
            }
        } else {
            start = LocalDate.of(y, 1, 1).atStartOfDay();
            end   = start.plusYears(1);
        }
        TransactionType type = TransactionType.from(transactionType);


        return transactionRepository
                .findAllTransactions(userId, start, end, categoryId, type)
                .stream()
                .map(t -> toTransactionResDTO(t))
                .toList();
    }

}
