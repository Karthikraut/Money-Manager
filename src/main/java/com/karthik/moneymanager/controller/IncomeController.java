package com.karthik.moneymanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.moneymanager.dto.IncomeDTO;
import com.karthik.moneymanager.service.IncomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/income")
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

}
