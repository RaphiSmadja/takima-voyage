package com.takima.demo.controller;

import com.takima.demo.dto.VoyageurDTO;
import com.takima.demo.model.CategoryType;
import com.takima.demo.service.VoyageurService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoyageurControllerTest {

    @InjectMocks
    private VoyageurController voyageurController;

    @Mock
    private VoyageurService voyageurService;

    @Test
    void testAddVoyageur_Success() {
        VoyageurDTO voyageurToAdd = new VoyageurDTO(null, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);
        VoyageurDTO savedVoyageur = new VoyageurDTO(1L, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);

        when(voyageurService.addVoyageur(voyageurToAdd)).thenReturn(savedVoyageur);

        ResponseEntity<VoyageurDTO> response = voyageurController.addVoyageur(voyageurToAdd);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Alice Dupont", Objects.requireNonNull(response.getBody()).getNom());
        verify(voyageurService, times(1)).addVoyageur(voyageurToAdd);
    }

    @Test
    void testGetAllVoyageurs_Success() {
        List<VoyageurDTO> voyageurs = List.of(
                new VoyageurDTO(1L, "Alice Dupont", "alice@example.com", "0610203040",
                        LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L),
                new VoyageurDTO(2L, "Bob Martin", "bob@example.com", "0620304050",
                        LocalDate.of(1985, 5, 15), LocalDate.of(2024, 11, 19), 2L, CategoryType.ECONOMY, 2L)
        );

        when(voyageurService.getAllVoyageurs()).thenReturn(voyageurs);

        ResponseEntity<List<VoyageurDTO>> response = voyageurController.getAllVoyageurs();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals("Alice Dupont", response.getBody().get(0).getNom());
        verify(voyageurService, times(1)).getAllVoyageurs();
    }

    @Test
    void testGetVoyageurById_Success() {
        VoyageurDTO voyageur = new VoyageurDTO(1L, "Alice Dupont", "alice@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);

        when(voyageurService.getVoyageurById(1L)).thenReturn(voyageur);

        ResponseEntity<VoyageurDTO> response = voyageurController.getVoyageurById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alice Dupont", Objects.requireNonNull(response.getBody()).getNom());
        verify(voyageurService, times(1)).getVoyageurById(1L);
    }

    @Test
    void testGetVoyageurById_NotFound() {
        when(voyageurService.getVoyageurById(999L)).thenThrow(new EntityNotFoundException("Voyageur introuvable."));

        assertThrows(EntityNotFoundException.class, () -> voyageurController.getVoyageurById(999L));
        verify(voyageurService, times(1)).getVoyageurById(999L);
    }

    @Test
    void testUpdateVoyageur_Success() {
        VoyageurDTO voyageurToUpdate = new VoyageurDTO(null, "Alice Updated", "alice.updated@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);
        VoyageurDTO updatedVoyageur = new VoyageurDTO(1L, "Alice Updated", "alice.updated@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);

        when(voyageurService.updateVoyageur(1L, voyageurToUpdate)).thenReturn(updatedVoyageur);

        ResponseEntity<VoyageurDTO> response = voyageurController.updateVoyageur(1L, voyageurToUpdate);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alice Updated", Objects.requireNonNull(response.getBody()).getNom());
        verify(voyageurService, times(1)).updateVoyageur(1L, voyageurToUpdate);
    }

    @Test
    void testUpdateVoyageur_NotFound() {
        VoyageurDTO voyageurToUpdate = new VoyageurDTO(null, "Non-existent", "nonexistent@example.com", "0610203040",
                LocalDate.of(1990, 1, 1), LocalDate.of(2024, 11, 20), 1L, CategoryType.BUSINESS, 1L);

        when(voyageurService.updateVoyageur(999L, voyageurToUpdate)).thenThrow(new EntityNotFoundException("Voyageur introuvable."));

        assertThrows(EntityNotFoundException.class, () -> voyageurController.updateVoyageur(999L, voyageurToUpdate));
        verify(voyageurService, times(1)).updateVoyageur(999L, voyageurToUpdate);
    }

    @Test
    void testDeleteVoyageur_Success() {
        doNothing().when(voyageurService).deleteVoyageur(1L);

        ResponseEntity<Void> response = voyageurController.deleteVoyageur(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(voyageurService, times(1)).deleteVoyageur(1L);
    }

    @Test
    void testDeleteVoyageur_NotFound() {
        doThrow(new EntityNotFoundException("Voyageur introuvable.")).when(voyageurService).deleteVoyageur(999L);

        assertThrows(EntityNotFoundException.class, () -> voyageurController.deleteVoyageur(999L));
        verify(voyageurService, times(1)).deleteVoyageur(999L);
    }
}
