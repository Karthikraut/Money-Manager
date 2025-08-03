package com.karthik.moneymanager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.karthik.moneymanager.dto.IncomeDTO;
import com.karthik.moneymanager.service.IncomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/incomes")
public class IncomeController {
    private final IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDTO> addIncome(@RequestBody IncomeDTO incomeDto) {
        // Validate and process the incomeDto
        // Call the service to add the income
        IncomeDTO addedIncome = incomeService.addIncome(incomeDto);
        
        // Return the added income as a response
        return ResponseEntity.ok(addedIncome);
    }

    @GetMapping
    public ResponseEntity<List<IncomeDTO> > getCurrentMonthExpensesForCurrentUser() {
        List<IncomeDTO> list = incomeService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{incomeId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long incomeId) {
           incomeService.deleteIncome(incomeId);
      return ResponseEntity.noContent().build();
    }
}
