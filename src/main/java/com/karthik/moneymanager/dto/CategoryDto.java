package com.karthik.moneymanager.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    
    private Long id;
    private Long profileId;
    private String name;
    private String icon;
    private String type;
    private LocalTime createdAt;
    private LocalTime updatedAt;

}
