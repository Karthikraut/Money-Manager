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

import com.karthik.moneymanager.dto.ExpenseDTO;
import com.karthik.moneymanager.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addIncome(@RequestBody ExpenseDTO expenseDTO) {
      
        ExpenseDTO addedExpense = expenseService.addExpense(expenseDTO);
        return ResponseEntity.ok(addedExpense);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO> > getCurrentMonthExpensesForCurrentUser() {
        List<ExpenseDTO> list = expenseService.getCurrentMonthExpensesForCurrentUser();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }

}
