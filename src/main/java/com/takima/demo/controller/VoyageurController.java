package com.takima.demo.controller;

import com.takima.demo.model.Voyageur;
import com.takima.demo.service.VoyageurService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voyageurs")
public class VoyageurController {

    private final VoyageurService voyageurService;

    public VoyageurController(VoyageurService voyageurService) {
        this.voyageurService = voyageurService;
    }

    @PostMapping("/voyages/{voyageId}")
    public Voyageur addVoyageurToVoyage(@PathVariable Long voyageId, @RequestBody Voyageur voyageur) {
        return voyageurService.addVoyageurToVoyage(voyageId, voyageur);
    }
}
