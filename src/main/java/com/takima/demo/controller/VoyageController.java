package com.takima.demo.controller;

import com.takima.demo.dto.VoyageDTO;
import com.takima.demo.service.VoyageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voyages")
@Tag(name = "Voyages", description = "Endpoints pour gérer les voyages")
public class VoyageController {

    private final VoyageService voyageService;

    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @Operation(summary = "Récupérer tous les voyages", description = "Retourne une liste de tous les voyages disponibles")
    @GetMapping
    public ResponseEntity<List<VoyageDTO>> getAllVoyages() {
        return ResponseEntity.ok(voyageService.getAllVoyages());
    }

    @Operation(summary = "Récupérer un voyage par ID", description = "Retourne un voyage correspondant à l'ID fourni")
    @GetMapping("/{id}")
    public ResponseEntity<VoyageDTO> getVoyageById(@PathVariable Long id) {
        return ResponseEntity.ok(voyageService.getVoyageById(id));
    }

    @Operation(summary = "Créer un nouveau voyage", description = "Ajoute un nouveau voyage dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voyage créé avec succès", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur", content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<VoyageDTO> createVoyage(@RequestBody VoyageDTO voyageDTO) {
        try {
            VoyageDTO createdVoyage = voyageService.createVoyage(voyageDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVoyage);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Modifier un voyage", description = "Modifier un voyage dans la base de données")
    @PutMapping("/{id}")
    public ResponseEntity<VoyageDTO> updateVoyage(@PathVariable Long id, @RequestBody VoyageDTO voyageDetails) {
        try {
            VoyageDTO updatedVoyage = voyageService.updateVoyage(id, voyageDetails);
            return ResponseEntity.ok(updatedVoyage);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Supprimer un voyage", description = "Supprimer un voyage dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voyage supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Voyage introuvable"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoyage(@PathVariable Long id) {
        try {
            voyageService.deleteVoyage(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
