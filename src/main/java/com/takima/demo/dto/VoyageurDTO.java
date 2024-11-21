package com.takima.demo.dto;

import com.takima.demo.model.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoyageurDTO {
    private Long id; // Identifiant unique du voyageur
    private String nom; // Nom du voyageur
    private String email; // Email du voyageur
    private String telephone; // Téléphone
    private LocalDate dateNaissance; // Date de naissance
    private LocalDate dateReservation; // Date de réservation
    private Long categoryId;
    private CategoryType type;
    private Long voyageId;
}
