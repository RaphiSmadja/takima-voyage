package com.takima.demo.service;

import com.takima.demo.dto.CategoryDTO;
import com.takima.demo.dto.VoyageDTO;
import com.takima.demo.exception.ResourceNotFoundException;
import com.takima.demo.mapper.CategoryMapper;
import com.takima.demo.mapper.VoyageMapper;
import com.takima.demo.model.Category;
import com.takima.demo.model.CategoryType;
import com.takima.demo.model.Voyage;
import com.takima.demo.repository.VoyageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoyageServiceTest {

    @InjectMocks
    private VoyageService voyageService;

    @Mock
    private VoyageRepository voyageRepository;

    @Mock
    private VoyageMapper voyageMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    void testGetAllVoyages_Success() {
        List<Voyage> voyages = List.of(
                new Voyage(1L, "Paris", 100.0, new ArrayList<>()),
                new Voyage(2L, "New York", 200.0, new ArrayList<>())
        );
        List<VoyageDTO> voyageDTOs = List.of(
                new VoyageDTO(1L, "Paris", 100.0, null),
                new VoyageDTO(2L, "New York", 200.0, null)
        );

        when(voyageRepository.findAll()).thenReturn(voyages);
        when(voyageMapper.toDTO(voyages.get(0))).thenReturn(voyageDTOs.get(0));
        when(voyageMapper.toDTO(voyages.get(1))).thenReturn(voyageDTOs.get(1));

        List<VoyageDTO> result = voyageService.getAllVoyages();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Paris", result.get(0).getDestination());
        verify(voyageRepository, times(1)).findAll();
        verify(voyageMapper, times(2)).toDTO(any(Voyage.class));
    }

    @Test
    void testGetVoyageById_Success() {
        Voyage voyage = new Voyage(1L, "Paris", 100.0, new ArrayList<>());
        VoyageDTO voyageDTO = new VoyageDTO(1L, "Paris", 100.0, null);

        when(voyageRepository.findById(1L)).thenReturn(Optional.of(voyage));
        when(voyageMapper.toDTO(voyage)).thenReturn(voyageDTO);

        VoyageDTO result = voyageService.getVoyageById(1L);

        assertNotNull(result);
        assertEquals("Paris", result.getDestination());
        verify(voyageRepository, times(1)).findById(1L);
        verify(voyageMapper, times(1)).toDTO(voyage);
    }

    @Test
    void testGetVoyageById_NotFound() {
        when(voyageRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> voyageService.getVoyageById(999L));
        assertEquals("Voyage introuvable.", exception.getMessage());
        verify(voyageRepository, times(1)).findById(999L);
        verify(voyageMapper, never()).toDTO(any(Voyage.class));
    }

    @Test
    void testCreateVoyage_Success() {
        VoyageDTO voyageDTO = new VoyageDTO(null, "Tokyo", 300.0, new ArrayList<>());
        Voyage voyage = new Voyage(null, "Tokyo", 300.0, new ArrayList<>());
        Voyage savedVoyage = new Voyage(1L, "Tokyo", 300.0, new ArrayList<>());
        VoyageDTO savedVoyageDTO = new VoyageDTO(1L, "Tokyo", 300.0, new ArrayList<>());

        when(voyageMapper.toEntity(voyageDTO)).thenReturn(voyage);
        when(voyageRepository.save(voyage)).thenReturn(savedVoyage);
        when(voyageMapper.toDTO(savedVoyage)).thenReturn(savedVoyageDTO);

        VoyageDTO result = voyageService.createVoyage(voyageDTO);

        assertNotNull(result);
        assertEquals("Tokyo", result.getDestination());
        verify(voyageMapper, times(1)).toEntity(voyageDTO);
        verify(voyageRepository, times(1)).save(voyage);
        verify(voyageMapper, times(1)).toDTO(savedVoyage);
    }

    @Test
    void testUpdateVoyage_Success() {
        Voyage existingVoyage = new Voyage(1L, "Paris", 100.0, new ArrayList<>());
        VoyageDTO voyageDTO = new VoyageDTO(null, "Updated Paris", 150.0, new ArrayList<>());
        Voyage updatedVoyage = new Voyage(1L, "Updated Paris", 150.0, new ArrayList<>());
        VoyageDTO updatedVoyageDTO = new VoyageDTO(1L, "Updated Paris", 150.0, new ArrayList<>());

        when(voyageRepository.findById(1L)).thenReturn(Optional.of(existingVoyage));
        when(voyageRepository.save(existingVoyage)).thenReturn(updatedVoyage);
        when(voyageMapper.toDTO(updatedVoyage)).thenReturn(updatedVoyageDTO);

        VoyageDTO result = voyageService.updateVoyage(1L, voyageDTO);

        assertNotNull(result);
        assertEquals("Updated Paris", result.getDestination());
        verify(voyageRepository, times(1)).findById(1L);
        verify(voyageRepository, times(1)).save(existingVoyage);
        verify(voyageMapper, times(1)).toDTO(updatedVoyage);
    }

    @Test
    void testUpdateVoyage_WithCategories() {
        // Données initiales
        Voyage existingVoyage = new Voyage(1L, "Paris", 100.0, new ArrayList<>());

        CategoryDTO categoryDTO1 = new CategoryDTO(1L, CategoryType.ECONOMY, 100, 10);
        CategoryDTO categoryDTO2 = new CategoryDTO(2L, CategoryType.BUSINESS, 50, 5);
        List<CategoryDTO> categoryDTOs = List.of(categoryDTO1, categoryDTO2);

        VoyageDTO voyageDetails = new VoyageDTO(null, "Updated Paris", 150.0, categoryDTOs);

        Category category1 = new Category(1L, CategoryType.ECONOMY, 100, 10, existingVoyage, new ArrayList<>());
        Category category2 = new Category(2L, CategoryType.BUSINESS, 50, 5, existingVoyage, new ArrayList<>());
        List<Category> updatedCategories = List.of(category1, category2);

        Voyage updatedVoyage = new Voyage(1L, "Updated Paris", 150.0, updatedCategories);
        VoyageDTO updatedVoyageDTO = new VoyageDTO(1L, "Updated Paris", 150.0, categoryDTOs);

        // Ajoute un log pour déboguer
        System.out.println("ID utilisé dans le test : " + 1L);

        // Mocks
        when(voyageRepository.findById(1L)).thenReturn(Optional.of(existingVoyage)); // Vérifie que cet ID est correct
        when(categoryMapper.toEntity(categoryDTO1)).thenReturn(category1);
        when(categoryMapper.toEntity(categoryDTO2)).thenReturn(category2);
        when(voyageRepository.save(existingVoyage)).thenReturn(updatedVoyage);
        when(voyageMapper.toDTO(updatedVoyage)).thenReturn(updatedVoyageDTO);

        // Appel de la méthode
        VoyageDTO result = voyageService.updateVoyage(1L, voyageDetails);

        // Assertions
        assertNotNull(result);
        assertEquals("Updated Paris", result.getDestination());
        assertEquals(150.0, result.getPrixDeBase());
        assertEquals(2, result.getCategories().size());
        assertEquals(CategoryType.ECONOMY, result.getCategories().get(0).getType());
        assertEquals(CategoryType.BUSINESS, result.getCategories().get(1).getType());

        // Vérification des interactions
        verify(voyageRepository, times(1)).findById(1L);
        verify(categoryMapper, times(1)).toEntity(categoryDTO1);
        verify(categoryMapper, times(1)).toEntity(categoryDTO2);
        verify(voyageRepository, times(1)).save(existingVoyage);
        verify(voyageMapper, times(1)).toDTO(updatedVoyage);
    }
    @Test
    void testUpdateVoyage_NotFound() {
        when(voyageRepository.findById(999L)).thenReturn(Optional.empty());

        VoyageDTO voyageDTO = new VoyageDTO(null, "Updated Paris", 150.0, new ArrayList<>());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> voyageService.updateVoyage(999L, voyageDTO));
        assertEquals("Voyage avec l'ID 999 introuvable", exception.getMessage());
        verify(voyageRepository, times(1)).findById(999L);
        verify(voyageRepository, never()).save(any(Voyage.class));
        verify(voyageMapper, never()).toDTO(any(Voyage.class));
    }

    @Test
    void testDeleteVoyage_Success() {
        when(voyageRepository.existsById(1L)).thenReturn(true);

        voyageService.deleteVoyage(1L);

        verify(voyageRepository, times(1)).existsById(1L);
        verify(voyageRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVoyage_NotFound() {
        when(voyageRepository.existsById(999L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> voyageService.deleteVoyage(999L));
        assertEquals("Le voyage avec l'ID 999 est introuvable.", exception.getMessage());
        verify(voyageRepository, times(1)).existsById(999L);
        verify(voyageRepository, never()).deleteById(anyLong());
    }
}
