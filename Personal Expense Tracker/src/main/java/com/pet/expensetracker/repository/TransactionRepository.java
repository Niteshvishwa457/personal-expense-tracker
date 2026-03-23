package com.pet.expensetracker.repository;

import com.pet.expensetracker.entity.Transaction;
import com.pet.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserOrderByTransactionDateDesc(User user);

    @Query("SELECT t FROM Transaction t WHERE t.user = :user " +
           "AND (:startDate IS NULL OR t.transactionDate >= :startDate) " +
           "AND (:endDate IS NULL OR t.transactionDate <= :endDate) " +
           "AND (:categoryName IS NULL OR t.category.categoryName = :categoryName) " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> filterTransactions(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("categoryName") String categoryName
    );

    @Query("SELECT t FROM Transaction t WHERE t.user = :user " +
           "AND MONTH(t.transactionDate) = :month " +
           "AND YEAR(t.transactionDate) = :year")
    List<Transaction> findByUserAndMonthAndYear(
            @Param("user") User user,
            @Param("month") int month,
            @Param("year") int year
    );
}
