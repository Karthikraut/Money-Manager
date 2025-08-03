package com.karthik.moneymanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.moneymanager.dto.ExpenseDTO;
import com.karthik.moneymanager.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addIncome(@RequestBody ExpenseDTO expenseDTO) {
      
        ExpenseDTO addedExpense = expenseService.addExpense(expenseDTO);
        return ResponseEntity.ok(addedExpense);
    }

}
