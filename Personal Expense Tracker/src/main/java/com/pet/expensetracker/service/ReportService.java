package com.pet.expensetracker.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pet.expensetracker.dto.ReportDto;
import com.pet.expensetracker.entity.Transaction;
import com.pet.expensetracker.entity.User;
import com.pet.expensetracker.repository.TransactionRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    public ReportDto getMonthlyReport(User user, int month, int year) {
        List<Transaction> transactions = transactionRepository.findByUserAndMonthAndYear(user, month, year);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        Map<String, BigDecimal> categoryExpenses = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.getType() == Transaction.TransactionType.INCOME) {
                totalIncome = totalIncome.add(t.getAmount());
            } else {
                totalExpenses = totalExpenses.add(t.getAmount());
                String categoryName = t.getCategory().getCategoryName();
                categoryExpenses.put(categoryName, categoryExpenses.getOrDefault(categoryName, BigDecimal.ZERO).add(t.getAmount()));
            }
        }

        return ReportDto.builder()
                .totalIncome(totalIncome)
                .totalExpenses(totalExpenses)
                .balance(totalIncome.subtract(totalExpenses))
                .categoryExpenses(categoryExpenses)
                .build();
    }

    public void exportToPdf(User user, int month, int year, HttpServletResponse response) throws DocumentException, IOException {
        List<Transaction> transactions = transactionRepository.findByUserAndMonthAndYear(user, month, year);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph("Personal Expense Report - " + month + "/" + year, fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph("User: " + user.getUsername()));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("Date");
        table.addCell("Type");
        table.addCell("Category");
        table.addCell("Description");
        table.addCell("Amount");

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction t : transactions) {
            table.addCell(t.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            table.addCell(t.getType().toString());
            table.addCell(t.getCategory().getCategoryName());
            table.addCell(t.getDescription() != null ? t.getDescription() : "");
            table.addCell(t.getAmount().toString());
            if (t.getType() == Transaction.TransactionType.INCOME) {
                totalIncome = totalIncome.add(t.getAmount());
            } else {
                totalExpenses = totalExpenses.add(t.getAmount());
            }
        }

        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total Income: " + totalIncome));
        document.add(new Paragraph("Total Expenses: " + totalExpenses));
        document.add(new Paragraph("Balance: " + totalIncome.subtract(totalExpenses)));

        document.close();
    }
}
