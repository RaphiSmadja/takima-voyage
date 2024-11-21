package com.takima.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoyageDTO {
    private Long id;
    private String destination;
    private double prixDeBase;
    private List<CategoryDTO> categories;
}