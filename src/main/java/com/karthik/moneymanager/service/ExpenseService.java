package com.karthik.moneymanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


import org.springframework.stereotype.Service;

import com.karthik.moneymanager.dto.ExpenseDTO;
import com.karthik.moneymanager.entity.CategoryEntity;
import com.karthik.moneymanager.entity.ExpenseEntity;
import com.karthik.moneymanager.entity.ProfileEntity;
import com.karthik.moneymanager.repository.CategoryRepository;
import com.karthik.moneymanager.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final ProfileService profileService;

    public ExpenseDTO addExpense(ExpenseDTO expenseDto) {

        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        ExpenseEntity newExpense = toEntity(expenseDto, profile, category);
        newExpense = expenseRepository.save(newExpense);

        return toDTO(newExpense);
    }

    //Retrives all expenses for the current profile for current month
    public List<ExpenseDTO> getCurrentMonthExpensesForCurrentUser(){
        ProfileEntity profile = profileService.getCurrentProfile();
        LocalDate now = LocalDate.now();
        LocalDate startOfMonth = now.withDayOfMonth(1);
        LocalDate endOfMonth = now.withDayOfMonth(now.lengthOfMonth());
        List<ExpenseEntity> list = expenseRepository.findByProfileIdAndDateBetween(profile.getId(), startOfMonth, endOfMonth);
        return list.stream()
                .map(this::toDTO)
                .toList();
    }

    //Delete expense by id for current user
    public void deleteExpense(Long expenseId) {
        ProfileEntity profile = profileService.getCurrentProfile();
        ExpenseEntity expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        if(!expense.getProfile().getId().equals(profile.getId())) {
            throw new IllegalArgumentException("Expense does not belong to the current user");
        }

        expenseRepository.deleteById(expenseId);

    }

    //Get latest 5 expenses for the current user
    public List<ExpenseDTO> getLatest5ExpensesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<ExpenseEntity> list = expenseRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
        return list.stream()
                .map(this::toDTO)
                .toList();
    }

    public BigDecimal getTotalExpenseForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        BigDecimal totalExpense = expenseRepository.findTotalExpenseByProfileId(profile.getId());
        return totalExpense != null ? totalExpense : BigDecimal.ZERO;
    }

    private ExpenseEntity toEntity(ExpenseDTO expenseDto, ProfileEntity profile, CategoryEntity category) {
        if (expenseDto == null) {
            return null;
        }

        // Convert ExpenseDTO to ExpenseEntity
        return ExpenseEntity.builder()
                .id(expenseDto.getId())
                .name(expenseDto.getName())
                .icon(expenseDto.getIcon())
                .amount(expenseDto.getAmount())
                .date(expenseDto.getDate() != null ? expenseDto.getDate() : null)
                .profile(profile)
                .category(category)
                .build();
    }

    private ExpenseDTO toDTO(ExpenseEntity expenseEntity) {

        // Convert ExpenseEntity to ExpenseDTO
        return ExpenseDTO.builder()
                .id(expenseEntity.getId())
                .name(expenseEntity.getName())
                .icon(expenseEntity.getIcon())
                .amount(expenseEntity.getAmount())
                .date(expenseEntity.getDate())
                .categoryId(expenseEntity.getCategory() != null ? expenseEntity.getCategory().getId() : null)
                .categoryName(expenseEntity.getCategory() != null ? expenseEntity.getCategory().getName() : null)
                .createdAt(expenseEntity.getCreatedAt())
                .updatedAt(expenseEntity.getUpdatedAt())
                .build();
    }

}
