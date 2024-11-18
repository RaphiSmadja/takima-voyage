package com.takima.demo.controller;

import com.takima.demo.model.Voyage;
import com.takima.demo.service.VoyageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public List<Voyage> getAllVoyages() {
        return voyageService.getAllVoyages();
    }

    @Operation(summary = "Récupérer un voyage par ID", description = "Retourne un voyage correspondant à l'ID fourni")
    @GetMapping("/{id}")
    public Voyage getVoyageById(@PathVariable Long id) {
        return voyageService.getVoyageById(id);
    }

    @Operation(summary = "Créer un nouveau voyage", description = "Ajoute un nouveau voyage dans la base de données")
    @PostMapping
    public Voyage createVoyage(@RequestBody Voyage voyage) {
        return voyageService.createVoyage(voyage);
    }

    @PutMapping("/{id}")
    public Voyage updateVoyage(@PathVariable Long id, @RequestBody Voyage voyageDetails) {
        return voyageService.updateVoyage(id, voyageDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteVoyage(@PathVariable Long id) {
        voyageService.deleteVoyage(id);
    }
}
