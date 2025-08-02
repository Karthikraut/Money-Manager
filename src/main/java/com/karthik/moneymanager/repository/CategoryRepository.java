package com.karthik.moneymanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karthik.moneymanager.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    // This interface will automatically provide CRUD operations for CategoryEntity
    // No additional methods are needed unless you want to add custom queries


    // select * from tbl_categories where profile_id = ?1
    // This is a custom query method to find categories by profile ID
    List<CategoryEntity> findByProfileId(Long profileId);
    // This method will allow you to find categories by the profile ID

    // select * from tbl_categories where id = ?1 and profile_id = ?2
    // Optional what it does is it will return an empty object if nothing is found
    // instead of throwing an exception
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);
    // This method will allow you to find a category by its ID and profile ID

    // select * from tbl_categories where type = ?1 and profile_id = ?2
    List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);
    // This method will allow you to find categories by type and profile ID

    Boolean existsByNameAndProfileId(String name, Long profileId);
    // This method will allow you to check if a category with the given name and for a specific profile exists
    
} 
