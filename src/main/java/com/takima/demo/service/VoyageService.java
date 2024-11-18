package com.takima.demo.service;

import com.takima.demo.model.Voyage;
import com.takima.demo.repository.VoyageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {

    private final VoyageRepository voyageRepository;

    public VoyageService(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    public List<Voyage> getAllVoyages() {
        return voyageRepository.findAll();
    }

    public Voyage getVoyageById(Long id) {
        return voyageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voyage introuvable."));
    }

    public List<Voyage> getVoyagesByDestination(String destination) {
        return voyageRepository.findByDestination(destination);
    }

    public Voyage createVoyage(Voyage voyage) {
        return voyageRepository.save(voyage);
    }

    public Voyage updateVoyage(Long id, Voyage voyageDetails) {
        Voyage voyage = getVoyageById(id);
        voyage.setDestination(voyageDetails.getDestination());
        voyage.setPrixDeBase(voyageDetails.getPrixDeBase());
        voyage.setCategories(voyageDetails.getCategories());
        return voyageRepository.save(voyage);
    }

    public void deleteVoyage(Long id) {
        voyageRepository.deleteById(id);
    }
}
