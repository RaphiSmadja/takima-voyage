package com.takima.demo.dto;

import com.takima.demo.model.CategoryType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VoyageurDTO {
    private Long id;
    private String nom;
    private String email;
    private Long voyageId;
    private CategoryType type;
    private String telephone;
    private LocalDate dateNaissance;
    private LocalDate dateReservation;
}
