package com.pet.expensetracker.controller;

import com.pet.expensetracker.dto.TransactionDto;
import com.pet.expensetracker.entity.User;
import com.pet.expensetracker.service.TransactionService;
import com.pet.expensetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Current user not found"));
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDto dto) {
        return ResponseEntity.ok(transactionService.addTransaction(dto, getCurrentUser()));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactions() {
        return ResponseEntity.ok(transactionService.getUserTransactions(getCurrentUser()));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TransactionDto>> filterTransactions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String categoryName) {
        return ResponseEntity.ok(transactionService.filterTransactions(getCurrentUser(), startDate, endDate, categoryName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id, @RequestBody TransactionDto dto) {
        return ResponseEntity.ok(transactionService.updateTransaction(id, dto, getCurrentUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id, getCurrentUser());
        return ResponseEntity.ok("Deleted successfully");
    }
}
