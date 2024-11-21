package com.takima.demo.controller;

import com.takima.demo.dto.VoyageDTO;
import com.takima.demo.mapper.CategoryMapper;
import com.takima.demo.mapper.VoyageMapper;
import com.takima.demo.repository.VoyageRepository;
import com.takima.demo.service.VoyageService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoyageControllerTest {
    @InjectMocks
    private VoyageController voyageController;
    @Mock
    private VoyageService voyageService;

    @Test
    void testGetAllVoyages_Success() {
        List<VoyageDTO> voyages = List.of(
                new VoyageDTO(1L, "Paris", 100.0, null),
                new VoyageDTO(2L, "New York", 200.0, null)
        );

        when(voyageService.getAllVoyages()).thenReturn(voyages);

        ResponseEntity<List<VoyageDTO>> response = voyageController.getAllVoyages();

        assertNotNull(Objects.requireNonNull(response.getBody()));
        assertEquals(2, response.getBody().size());

        assertEquals("Paris", response.getBody().get(0).getDestination());
        assertEquals("New York", response.getBody().get(1).getDestination());
    }

    @Test
    void testGetVoyageById_Success() {
        VoyageDTO voyage = new VoyageDTO(1L, "Paris", 100.0, null);

        when(voyageService.getVoyageById(1L)).thenReturn(voyage);

        ResponseEntity<VoyageDTO> response = voyageController.getVoyageById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Paris", Objects.requireNonNull(response.getBody()).getDestination());
    }

    @Test
    void testGetVoyageById_NotFound() {
        when(voyageService.getVoyageById(999L)).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> voyageController.getVoyageById(999L));
    }

    @Test
    void testCreateVoyage_Success() {
        VoyageDTO voyageToCreate = new VoyageDTO(null, "Tokyo", 300.0, null);
        VoyageDTO createdVoyage = new VoyageDTO(1L, "Tokyo", 300.0, null);

        when(voyageService.createVoyage(voyageToCreate)).thenReturn(createdVoyage);

        ResponseEntity<VoyageDTO> response = voyageController.createVoyage(voyageToCreate);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Tokyo", Objects.requireNonNull(response.getBody()).getDestination());
    }

    @Test
    void testCreateVoyage_BadRequest() {
        VoyageDTO voyageToCreate = new VoyageDTO(null, "", -10.0, null);

        when(voyageService.createVoyage(voyageToCreate)).thenThrow(new IllegalArgumentException());

        ResponseEntity<VoyageDTO> response = voyageController.createVoyage(voyageToCreate);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateVoyage_Success() {
        VoyageDTO voyageToUpdate = new VoyageDTO(null, "Tokyo Updated", 350.0, null);
        VoyageDTO updatedVoyage = new VoyageDTO(1L, "Tokyo Updated", 350.0, null);

        when(voyageService.updateVoyage(1L, voyageToUpdate)).thenReturn(updatedVoyage);

        ResponseEntity<VoyageDTO> response = voyageController.updateVoyage(1L, voyageToUpdate);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tokyo Updated", Objects.requireNonNull(response.getBody()).getDestination());
    }

    @Test
    void testUpdateVoyage_NotFound() {
        VoyageDTO voyageToUpdate = new VoyageDTO(null, "Non-existent", 200.0, null);

        when(voyageService.updateVoyage(eq(999L), any(VoyageDTO.class))).thenThrow(new EntityNotFoundException());

        ResponseEntity<VoyageDTO> response = voyageController.updateVoyage(999L, voyageToUpdate);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteVoyage_Success() {
        doNothing().when(voyageService).deleteVoyage(1L);

        ResponseEntity<Void> response = voyageController.deleteVoyage(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteVoyage_NotFound() {
        doThrow(new EntityNotFoundException()).when(voyageService).deleteVoyage(999L);

        ResponseEntity<Void> response = voyageController.deleteVoyage(999L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}