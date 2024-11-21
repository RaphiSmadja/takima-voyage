package com.takima.demo.service;

import com.takima.demo.dto.VoyageurDTO;
import com.takima.demo.exception.ResourceNotFoundException;
import com.takima.demo.mapper.VoyageurMapper;
import com.takima.demo.model.Category;
import com.takima.demo.model.Voyageur;
import com.takima.demo.repository.CategoryRepository;
import com.takima.demo.repository.VoyageurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageurService {

    private final VoyageurRepository voyageurRepository;
    private final CategoryRepository categoryRepository;
    private final VoyageurMapper voyageurMapper;

    public VoyageurService(VoyageurRepository voyageurRepository, CategoryRepository categoryRepository, VoyageurMapper voyageurMapper) {
        this.voyageurRepository = voyageurRepository;
        this.categoryRepository = categoryRepository;
        this.voyageurMapper = voyageurMapper;
    }

    public VoyageurDTO addVoyageur(VoyageurDTO voyageurDTO) {
        Category category = categoryRepository.findById(voyageurDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie introuvable avec l'ID " + voyageurDTO.getCategoryId()));

        Voyageur voyageur = voyageurMapper.toEntity(voyageurDTO);
        voyageur.setCategory(category);

        Voyageur savedVoyageur = voyageurRepository.save(voyageur);
        return voyageurMapper.toDTO(savedVoyageur);
    }

    public List<VoyageurDTO> getAllVoyageurs() {
        return voyageurRepository.findAll().stream()
                .map(voyageurMapper::toDTO)
                .toList();
    }

    public VoyageurDTO getVoyageurById(Long id) {
        Voyageur voyageur = voyageurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voyageur avec l'ID " + id + " introuvable."));
        return voyageurMapper.toDTO(voyageur);
    }

    public VoyageurDTO updateVoyageur(Long id, VoyageurDTO voyageurDTO) {
        Voyageur existingVoyageur = voyageurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voyageur avec l'ID " + id + " introuvable."));

        if (voyageurDTO.getNom() != null) existingVoyageur.setNom(voyageurDTO.getNom());
        if (voyageurDTO.getEmail() != null) existingVoyageur.setEmail(voyageurDTO.getEmail());
        if (voyageurDTO.getTelephone() != null) existingVoyageur.setTelephone(voyageurDTO.getTelephone());
        if (voyageurDTO.getDateNaissance() != null) existingVoyageur.setDateNaissance(voyageurDTO.getDateNaissance());
        if (voyageurDTO.getDateReservation() != null)
            existingVoyageur.setDateReservation(voyageurDTO.getDateReservation());

        Voyageur updatedVoyageur = voyageurRepository.save(existingVoyageur);
        return voyageurMapper.toDTO(updatedVoyageur);
    }

    public void deleteVoyageur(Long id) {
        if (!voyageurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Voyageur avec l'ID " + id + " introuvable.");
        }
        voyageurRepository.deleteById(id);
    }
}
