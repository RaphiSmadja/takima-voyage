package com.takima.demo.dto;

import com.takima.demo.model.CategoryType;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;
    private CategoryType type;
    private int capaciteTotale;
    private int placesReservees;
}
