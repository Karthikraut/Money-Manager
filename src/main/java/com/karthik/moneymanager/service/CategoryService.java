package com.karthik.moneymanager.service;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.karthik.moneymanager.dto.CategoryDto;
import com.karthik.moneymanager.entity.CategoryEntity;
import com.karthik.moneymanager.entity.ProfileEntity;
import com.karthik.moneymanager.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    // Add methods to handle category operations like create, update, delete, etc.
    private final ProfileService profileService;

    public CategoryDto saveCategory(CategoryDto categoryDto) {
        ProfileEntity profile = profileService.getCurrentProfile();
        if (categoryRepository.existsByNameAndProfileId(categoryDto.getName(), profile.getId())) {
            throw new RuntimeException("Category with this name already exists for this profile");
        }
        CategoryEntity newCategory = toEntity(categoryDto, profile);
        newCategory = categoryRepository.save(newCategory);
        return toDto(newCategory);

    }

    //Get categories for current user
    public List<CategoryDto> getCategoriesForCurrentUser() {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByProfileId(profile.getId());
        return categories.stream().map(this::toDto).toList();
    }

    //Get categories by type for current user
    public List<CategoryDto> getCategoriesByTypeForCurrentUser(String type) {
        ProfileEntity profile = profileService.getCurrentProfile();
        List<CategoryEntity> categories = categoryRepository.findByTypeAndProfileId(type, profile.getId());
        return categories.stream().map(this::toDto).toList();
    }

    public CategoryDto updateCategory(Long categoryId, CategoryDto dto){
        ProfileEntity profile = profileService.getCurrentProfile();
        CategoryEntity existingCategory = categoryRepository.findByIdAndProfileId(categoryId, profile.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        

        existingCategory.setName(dto.getName());
        existingCategory.setIcon(dto.getIcon());
        existingCategory.setType(dto.getType());
        existingCategory = categoryRepository.save(existingCategory);
        return toDto(existingCategory);
    }

    private CategoryEntity toEntity(CategoryDto categoryDto, ProfileEntity profile) {
        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .icon(categoryDto.getIcon())
                .type(categoryDto.getType())
                .profile(profile)
                .build();
    }

    private CategoryDto toDto(CategoryEntity categoryEntity) {
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .icon(categoryEntity.getIcon())
                .type(categoryEntity.getType())
                .createdAt(categoryEntity.getCreatedAt().toLocalTime())
                .updatedAt(categoryEntity.getUpdatedAt().toLocalTime())
                .profileId(categoryEntity.getProfile() != null ? categoryEntity.getProfile().getId() : null)
                .build();
    }

}
