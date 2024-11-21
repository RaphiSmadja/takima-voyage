package com.takima.demo.controller;

import com.takima.demo.dto.VoyageurDTO;
import com.takima.demo.service.VoyageurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voyageurs")
@Tag(name = "Voyageurs", description = "Endpoints pour gérer les voyageurs")
public class VoyageurController {

    private final VoyageurService voyageurService;

    public VoyageurController(VoyageurService voyageurService) {
        this.voyageurService = voyageurService;
    }

    @Operation(summary = "Ajouter un voyageur", description = "Ajoute un nouveau voyageur à un voyage et une catégorie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Voyageur ajouté avec succès"),
            @ApiResponse(responseCode = "404", description = "Voyage ou catégorie introuvable"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    public ResponseEntity<VoyageurDTO> addVoyageur(@RequestBody @Valid VoyageurDTO voyageurDTO) {
        VoyageurDTO savedVoyageur = voyageurService.addVoyageur(voyageurDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVoyageur);
    }

    @Operation(summary = "Récupérer tous les voyageurs", description = "Renvoie la liste de tous les voyageurs enregistrés.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des voyageurs récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<List<VoyageurDTO>> getAllVoyageurs() {
        List<VoyageurDTO> voyageurs = voyageurService.getAllVoyageurs();
        return ResponseEntity.ok(voyageurs);
    }

    @Operation(summary = "Récupérer un voyageur par ID", description = "Renvoie les détails d'un voyageur en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voyageur récupéré avec succès"),
            @ApiResponse(responseCode = "404", description = "Voyageur introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VoyageurDTO> getVoyageurById(@PathVariable Long id) {
        VoyageurDTO voyageur = voyageurService.getVoyageurById(id);
        return ResponseEntity.ok(voyageur);
    }

    @Operation(summary = "Mettre à jour un voyageur", description = "Met à jour les informations d'un voyageur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voyageur mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Voyageur introuvable")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VoyageurDTO> updateVoyageur(@PathVariable Long id, @RequestBody @Valid VoyageurDTO voyageurDTO) {
        VoyageurDTO updatedVoyageur = voyageurService.updateVoyageur(id, voyageurDTO);
        return ResponseEntity.ok(updatedVoyageur);
    }

    @Operation(summary = "Supprimer un voyageur", description = "Supprime un voyageur en fonction de son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voyageur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Voyageur introuvable")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoyageur(@PathVariable Long id) {
        voyageurService.deleteVoyageur(id);
        return ResponseEntity.ok().build();
    }
}
