package com.karthik.moneymanager.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.karthik.moneymanager.dto.ExpenseDTO;
import com.karthik.moneymanager.dto.IncomeDTO;
import com.karthik.moneymanager.dto.RecentTransactionDTO;
import com.karthik.moneymanager.entity.ProfileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final ProfileService profileService;

    public Map<String, Object> getDashboardData() {
        ProfileEntity profile = profileService.getCurrentProfile();
        Map<String, Object> returnValue = new LinkedHashMap<>();

        List<IncomeDTO> incomes = incomeService.getLatest5IncomesForCurrentUser();
        List<ExpenseDTO> expenses = expenseService.getLatest5ExpensesForCurrentUser();

        List<RecentTransactionDTO> recentTransactions = Stream.concat(
                incomes.stream().map(income -> RecentTransactionDTO.builder()
                        .id(income.getId())
                        .profileID(profile.getId())
                        .icon(income.getIcon())
                        .name(income.getName())
                        .amount(income.getAmount())
                        .date(income.getDate())
                        .createdAt(income.getCreatedAt())
                        .updatedAt(income.getUpdatedAt())
                        .type("income")
                        .build()),

                expenses.stream().map(expense -> RecentTransactionDTO.builder()
                        .id(expense.getId())
                        .profileID(profile.getId())
                        .icon(expense.getIcon()) // ✅ fixed (previously using expense.getCategory())
                        .name(expense.getName()) // ✅ fixed
                        .amount(expense.getAmount())
                        .date(expense.getDate())
                        .createdAt(expense.getCreatedAt())
                        .updatedAt(expense.getUpdatedAt())
                        .type("expense")
                        .build())
        )
        .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
        .collect(Collectors.toList());
        returnValue.put("totalBalance", incomeService.getTotalIncomeForCurrentUser().subtract(expenseService.getTotalExpenseForCurrentUser()));
        returnValue.put("totalIncome", incomeService.getTotalIncomeForCurrentUser());
        returnValue.put("totalExpense", expenseService.getTotalExpenseForCurrentUser());
        returnValue.put("recent5Expenses", expenses);
        returnValue.put("recent5Incomes", incomes);
        returnValue.put("recentTransactions", recentTransactions);
        
        return returnValue;
    }
}
