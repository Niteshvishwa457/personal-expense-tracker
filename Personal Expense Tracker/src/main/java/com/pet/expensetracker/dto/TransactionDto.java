package com.pet.expensetracker.dto;

import com.pet.expensetracker.entity.Transaction.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long transactionId;
    private String categoryName;
    private BigDecimal amount;
    private TransactionType type;
    private LocalDate transactionDate;
    private String description;
}
