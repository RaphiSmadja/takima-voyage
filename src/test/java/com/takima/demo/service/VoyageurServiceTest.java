package com.takima.demo.service;

import com.takima.demo.dto.VoyageurDTO;
import com.takima.demo.exception.ResourceNotFoundException;
import com.takima.demo.mapper.VoyageurMapper;
import com.takima.demo.model.Category;
import com.takima.demo.model.CategoryType;
import com.takima.demo.model.Voyageur;
import com.takima.demo.repository.CategoryRepository;
import com.takima.demo.repository.VoyageurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoyageurServiceTest {

    @InjectMocks
    private VoyageurService voyageurService;

    @Mock
    private VoyageurRepository voyageurRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private VoyageurMapper voyageurMapper;

    @Test
    void testAddVoyageur_Success() {
        VoyageurDTO voyageurDTO = new VoyageurDTO(null, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);
        Category category = new Category(1L, CategoryType.BUSINESS, 100, 10, null, new ArrayList<>());
        Voyageur voyageur = new Voyageur(null, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), category);
        Voyageur savedVoyageur = new Voyageur(1L, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), category);
        VoyageurDTO savedVoyageurDTO = new VoyageurDTO(1L, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(voyageurMapper.toEntity(voyageurDTO)).thenReturn(voyageur);
        when(voyageurRepository.save(voyageur)).thenReturn(savedVoyageur);
        when(voyageurMapper.toDTO(savedVoyageur)).thenReturn(savedVoyageurDTO);

        VoyageurDTO result = voyageurService.addVoyageur(voyageurDTO);

        assertNotNull(result);
        assertEquals("Alice Dupont", result.getNom());
        verify(categoryRepository, times(1)).findById(1L);
        verify(voyageurRepository, times(1)).save(voyageur);
        verify(voyageurMapper, times(1)).toDTO(savedVoyageur);
    }

    @Test
    void testAddVoyageur_CategoryNotFound() {
        VoyageurDTO voyageurDTO = new VoyageurDTO(null, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> voyageurService.addVoyageur(voyageurDTO));
        assertEquals("Catégorie introuvable avec l'ID 1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
        verify(voyageurRepository, never()).save(any(Voyageur.class));
    }

    @Test
    void testGetAllVoyageurs_Success() {
        List<Voyageur> voyageurs = List.of(
                new Voyageur(1L, "Alice Dupont", "alice@example.com", "0610203040",
                        LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), null),
                new Voyageur(2L, "Bob Martin", "bob@example.com", "0620304050",
                        LocalDate.of(1985, 5, 15), LocalDate.of(2024, 11, 19), null)
        );
        List<VoyageurDTO> voyageurDTOs = List.of(
                new VoyageurDTO(1L, "Alice Dupont", "alice@example.com", "0610203040",
                        LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L),
                new VoyageurDTO(2L, "Bob Martin", "bob@example.com", "0620304050",
                        LocalDate.of(1985, 5, 15), LocalDate.of(2024, 11, 19), 2L, CategoryType.ECONOMY, 2L)
        );

        when(voyageurRepository.findAll()).thenReturn(voyageurs);
        when(voyageurMapper.toDTO(voyageurs.get(0))).thenReturn(voyageurDTOs.get(0));
        when(voyageurMapper.toDTO(voyageurs.get(1))).thenReturn(voyageurDTOs.get(1));

        List<VoyageurDTO> result = voyageurService.getAllVoyageurs();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice Dupont", result.get(0).getNom());
        verify(voyageurRepository, times(1)).findAll();
        verify(voyageurMapper, times(2)).toDTO(any(Voyageur.class));
    }

    @Test
    void testGetVoyageurById_Success() {
        Voyageur voyageur = new Voyageur(1L, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), null);
        VoyageurDTO voyageurDTO = new VoyageurDTO(1L, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);

        when(voyageurRepository.findById(1L)).thenReturn(Optional.of(voyageur));
        when(voyageurMapper.toDTO(voyageur)).thenReturn(voyageurDTO);

        VoyageurDTO result = voyageurService.getVoyageurById(1L);

        assertNotNull(result);
        assertEquals("Alice Dupont", result.getNom());
        verify(voyageurRepository, times(1)).findById(1L);
        verify(voyageurMapper, times(1)).toDTO(voyageur);
    }

    @Test
    void testGetVoyageurById_NotFound() {
        when(voyageurRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> voyageurService.getVoyageurById(999L));
        assertEquals("Voyageur avec l'ID 999 introuvable.", exception.getMessage());
        verify(voyageurRepository, times(1)).findById(999L);
        verify(voyageurMapper, never()).toDTO(any(Voyageur.class));
    }

    @Test
    void testDeleteVoyageur_Success() {
        when(voyageurRepository.existsById(1L)).thenReturn(true);

        voyageurService.deleteVoyageur(1L);

        verify(voyageurRepository, times(1)).existsById(1L);
        verify(voyageurRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteVoyageur_NotFound() {
        when(voyageurRepository.existsById(999L)).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> voyageurService.deleteVoyageur(999L));
        assertEquals("Voyageur avec l'ID 999 introuvable.", exception.getMessage());
        verify(voyageurRepository, times(1)).existsById(999L);
        verify(voyageurRepository, never()).deleteById(anyLong());
    }
}
