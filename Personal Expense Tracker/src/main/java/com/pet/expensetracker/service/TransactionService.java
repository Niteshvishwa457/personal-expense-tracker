package com.pet.expensetracker.service;

import com.pet.expensetracker.dto.TransactionDto;
import com.pet.expensetracker.entity.Category;
import com.pet.expensetracker.entity.Transaction;
import com.pet.expensetracker.entity.User;
import com.pet.expensetracker.repository.TransactionRepository;
import com.pet.expensetracker.util.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    @Value("${app.encryption.key}")
    private String encryptionKey;

    public Transaction addTransaction(TransactionDto dto, User user) {
        Category category = categoryService.getCategoryByName(dto.getCategoryName());

        Transaction transaction = Transaction.builder()
                .user(user)
                .category(category)
                .amount(dto.getAmount())
                .type(dto.getType())
                .transactionDate(dto.getTransactionDate())
                .description(dto.getDescription())
                .build();

        // Encrypt description if sensitive data requirement applies
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            transaction.setEncryptedDescription(AESUtil.encrypt(dto.getDescription(), encryptionKey));
        }

        return transactionRepository.save(transaction);
    }

    public List<TransactionDto> getUserTransactions(User user) {
        return transactionRepository.findByUserOrderByTransactionDateDesc(user)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<TransactionDto> filterTransactions(User user, LocalDate startDate, LocalDate endDate, String categoryName) {
        return transactionRepository.filterTransactions(user, startDate, endDate, categoryName)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public void deleteTransaction(Long id, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (!transaction.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized to delete this transaction");
        }
        transactionRepository.delete(transaction);
    }

    public TransactionDto updateTransaction(Long id, TransactionDto dto, User user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (!transaction.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized to update this transaction");
        }

        Category category = categoryService.getCategoryByName(dto.getCategoryName());
        transaction.setCategory(category);
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType());
        transaction.setTransactionDate(dto.getTransactionDate());
        transaction.setDescription(dto.getDescription());
        
        if (dto.getDescription() != null && !dto.getDescription().isEmpty()) {
            transaction.setEncryptedDescription(AESUtil.encrypt(dto.getDescription(), encryptionKey));
        } else {
            transaction.setEncryptedDescription(null);
        }

        Transaction updated = transactionRepository.save(transaction);
        return mapToDto(updated);
    }

    private TransactionDto mapToDto(Transaction t) {
        // If we want to demonstrate decryption, we could pull from encryptedDescription
        // String desc = (t.getEncryptedDescription() != null) ? AESUtil.decrypt(t.getEncryptedDescription(), encryptionKey) : t.getDescription();
        return TransactionDto.builder()
                .transactionId(t.getTransactionId())
                .categoryName(t.getCategory().getCategoryName())
                .amount(t.getAmount())
                .type(t.getType())
                .transactionDate(t.getTransactionDate())
                .description(t.getDescription())
                .build();
    }
}
