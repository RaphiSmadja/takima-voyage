package com.takima.demo.dto;

import com.takima.demo.model.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private CategoryType type;
    private int capaciteTotale;
    private int placesReservees;
}
