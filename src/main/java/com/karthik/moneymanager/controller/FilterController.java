package com.karthik.moneymanager.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.moneymanager.dto.FilterDTO;
import com.karthik.moneymanager.service.ExpenseService;
import com.karthik.moneymanager.service.IncomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {
    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> filterTransactions(@RequestBody FilterDTO filterDTO) {
        // Preparing The data for filtering
        LocalDate startDate = filterDTO.getStartDate() != null ? filterDTO.getStartDate() : LocalDate.MIN;
        LocalDate endDate = filterDTO.getEndDate() != null ? filterDTO.getEndDate() : LocalDate.now();
        String keyword = filterDTO.getKeyword() != null ? filterDTO.getKeyword() : "";
        String sortField = filterDTO.getSortField() != null ? filterDTO.getSortField() : "date";
        Sort.Direction direction = "desc".equalsIgnoreCase(filterDTO.getSortOrder()) ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);

        if ("income".equalsIgnoreCase(filterDTO.getType())) {
            return ResponseEntity.ok(incomeService.filterIncome(startDate, endDate, keyword, sort));
        } else if ("expense".equalsIgnoreCase(filterDTO.getType())) {
            return ResponseEntity.ok(expenseService.filterExpense(startDate, endDate, keyword, sort));
        } else {
            return ResponseEntity.badRequest().body("Invalid type specified. Use 'income' or 'expense'.");
        }
    }
}
