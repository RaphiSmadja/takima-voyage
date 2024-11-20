package com.takima.demo.service;

import com.takima.demo.model.Voyage;
import com.takima.demo.model.Voyageur;
import com.takima.demo.repository.VoyageRepository;
import com.takima.demo.repository.VoyageurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageurService {

    private final VoyageurRepository voyageurRepository;

    private final VoyageRepository voyageRepository;

    public VoyageurService(VoyageRepository voyageRepository, VoyageurRepository voyageurRepository) {
        this.voyageRepository = voyageRepository;
        this.voyageurRepository = voyageurRepository;
    }

    public Voyageur addVoyageurToVoyage(Long voyageId, Voyageur voyageur) {
        Voyage voyage = voyageRepository.findById(voyageId)
                .orElseThrow(() -> new RuntimeException("Voyage introuvable."));

        return voyageurRepository.save(voyageur);
    }

    public List<Voyageur> getVoyageursByVoyage(Long voyageId) {
        Voyage voyage = voyageRepository.findById(voyageId)
                .orElseThrow(() -> new RuntimeException("Voyage introuvable."));
        return voyage.getCategories().stream()
                .flatMap(cat -> cat.getVoyageurs().stream())
                .toList();
    }
}
