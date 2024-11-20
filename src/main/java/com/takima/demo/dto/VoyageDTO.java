package com.takima.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class VoyageDTO {
    private Long id;
    private String destination;
    private double prixDeBase;
    private List<CategoryDTO> categories;
}