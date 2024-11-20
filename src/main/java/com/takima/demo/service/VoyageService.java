package com.takima.demo.service;

import com.takima.demo.dto.VoyageDTO;
import com.takima.demo.exception.ResourceNotFoundException;
import com.takima.demo.mapper.CategoryMapper;
import com.takima.demo.mapper.VoyageMapper;
import com.takima.demo.model.Category;
import com.takima.demo.model.Voyage;
import com.takima.demo.repository.VoyageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoyageService {

    private final VoyageRepository voyageRepository;
    private final CategoryMapper categoryMapper;
    private final VoyageMapper voyageMapper;

    public VoyageService(VoyageRepository voyageRepository, VoyageMapper voyageMapper, CategoryMapper categoryMapper) {
        this.voyageRepository = voyageRepository;
        this.voyageMapper = voyageMapper;
        this.categoryMapper = categoryMapper;
    }

    public List<VoyageDTO> getAllVoyages() {
        return voyageRepository.findAll().stream().map(voyageMapper::toDTO).collect(Collectors.toList());
    }

    public VoyageDTO getVoyageById(Long id) {
        return voyageMapper.toDTO(voyageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voyage introuvable.")));
    }

    public List<Voyage> getVoyagesByDestination(String destination) {
        return voyageRepository.findByDestination(destination);
    }

    public VoyageDTO createVoyage(VoyageDTO voyageDTO) {
        Voyage voyage = voyageMapper.toEntity(voyageDTO);

        if (voyage.getCategories() != null) {
            voyage.getCategories().forEach(category -> category.setVoyage(voyage));
        }

        Voyage savedVoyage = voyageRepository.save(voyage);
        return voyageMapper.toDTO(savedVoyage);
    }

    public VoyageDTO updateVoyage(Long id, VoyageDTO voyageDetails) {
        Voyage existingVoyage = voyageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voyage avec l'ID " + id + " introuvable"));

        if (voyageDetails.getDestination() != null) {
            existingVoyage.setDestination(voyageDetails.getDestination());
        }

        existingVoyage.setPrixDeBase(voyageDetails.getPrixDeBase());


        if (voyageDetails.getCategories() != null && !voyageDetails.getCategories().isEmpty()) {

            List<Category> updatedCategories = voyageDetails.getCategories().stream()
                    .map(categoryDTO -> {
                        Category category = categoryMapper.toEntity(categoryDTO);
                        category.setVoyage(existingVoyage);
                        return category;
                    })
                    .toList();

            existingVoyage.getCategories().clear();
            existingVoyage.getCategories().addAll(updatedCategories);
        }

        Voyage savedVoyage = voyageRepository.save(existingVoyage);
        return voyageMapper.toDTO(savedVoyage);
    }


    public void deleteVoyage(Long id) {
        if (!voyageRepository.existsById(id)) {
            throw new EntityNotFoundException("Le voyage avec l'ID " + id + " est introuvable.");
        }
        voyageRepository.deleteById(id);
    }
}
