package com.karthik.moneymanager.service;

import java.util.Optional;

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
