package com.karthik.moneymanager.dto;

import java.time.LocalDate;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FilterDTO {
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private String keyword;
    private String sortField; //date, amount, name
    private String sortOrder; //asc, desc
   
}
