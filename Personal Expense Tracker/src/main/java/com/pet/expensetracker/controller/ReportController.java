package com.pet.expensetracker.controller;

import com.lowagie.text.DocumentException;
import com.pet.expensetracker.dto.ReportDto;
import com.pet.expensetracker.entity.User;
import com.pet.expensetracker.service.ReportService;
import com.pet.expensetracker.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName()).orElseThrow(() -> new RuntimeException("Current user not found"));
    }

    @GetMapping("/monthly")
    public ResponseEntity<ReportDto> getMonthlyReport(@RequestParam int month, @RequestParam int year) {
        return ResponseEntity.ok(reportService.getMonthlyReport(getCurrentUser(), month, year));
    }

    @GetMapping("/export/pdf")
    public void exportToPdf(@RequestParam int month, @RequestParam int year, HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report_" + month + "_" + year + ".pdf";
        response.setHeader(headerKey, headerValue);

        reportService.exportToPdf(getCurrentUser(), month, year, response);
    }
}
