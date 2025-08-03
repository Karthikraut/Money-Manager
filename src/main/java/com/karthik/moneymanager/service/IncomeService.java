package com.karthik.moneymanager.service;

import org.springframework.stereotype.Service;

import com.karthik.moneymanager.dto.IncomeDTO;
import com.karthik.moneymanager.entity.CategoryEntity;
import com.karthik.moneymanager.entity.IncomeEntity;
import com.karthik.moneymanager.entity.ProfileEntity;
import com.karthik.moneymanager.repository.CategoryRepository;
import com.karthik.moneymanager.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final CategoryRepository categoryRepository;
    private final IncomeRepository incomeRepository;
    private final ProfileService profileService;

    public IncomeDTO addIncome(IncomeDTO incomeDto) {

        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity category = categoryRepository.findById(incomeDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        IncomeEntity newIncome = toEntity(incomeDto, profile, category);
        newIncome = incomeRepository.save(newIncome);
        return toDTO(newIncome);
    }

    // Convert IncomeDTO to IncomeEntity
    private IncomeEntity toEntity(IncomeDTO incomeDTO, ProfileEntity profile, CategoryEntity category) {
        if (incomeDTO == null) {
            return null;
        }

        return IncomeEntity.builder()
                .id(incomeDTO.getId())
                .name(incomeDTO.getName())
                .icon(incomeDTO.getIcon())
                .amount(incomeDTO.getAmount())
                .date(incomeDTO.getDate())
                .profile(profile)
                .category(category)
                .build();
    }

    // Convert IncomeEntity to IncomeDTO
    private IncomeDTO toDTO(IncomeEntity incomeEntity) {
        if (incomeEntity == null) {
            return null;
        }

        return IncomeDTO.builder()
                .id(incomeEntity.getId())
                .name(incomeEntity.getName())
                .icon(incomeEntity.getIcon())
                .amount(incomeEntity.getAmount())
                .date(incomeEntity.getDate() != null ? incomeEntity.getDate() : null)
                .categoryId(incomeEntity.getCategory() != null ? incomeEntity.getCategory().getId() : null)
                .categoryName(incomeEntity.getCategory() != null ? incomeEntity.getCategory().getName() : null)
                .createdAt(incomeEntity.getCreatedAt())
                .updatedAt(incomeEntity.getUpdatedAt())
                .build();
    }

}
