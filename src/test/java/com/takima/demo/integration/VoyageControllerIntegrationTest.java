package com.takima.demo.integration;

import com.takima.demo.controller.VoyageController;
import com.takima.demo.service.VoyageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VoyageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoyageService voyageService; // Mock explicit du service

    @MockBean
    private VoyageController voyageController;

    @Test
    void testGetAllVoyages_Success() throws Exception {
        mockMvc.perform(get("/voyages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].destination").value("Paris"));
    }

    @Test
    void testGetVoyageById_Success() throws Exception {
        mockMvc.perform(get("/voyages/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destination").value("Paris"))
                .andExpect(jsonPath("$.prixDeBase").value(100.0));
    }

    @Test
    void testGetVoyageById_NotFound() throws Exception {
        mockMvc.perform(get("/voyages/99") // Un ID inexistant
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateVoyage_Success() throws Exception {
        String newVoyage = """
                {
                    "destination": "Berlin",
                    "prixDeBase": 150.0,
                    "categories": []
                }
                """;

        mockMvc.perform(post("/voyages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newVoyage))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination").value("Berlin"))
                .andExpect(jsonPath("$.prixDeBase").value(150.0));
    }

    @Test
    void testCreateVoyage_BadRequest() throws Exception {
        String invalidVoyage = """
                {
                    "prixDeBase": 150.0
                }
                """;

        mockMvc.perform(post("/voyages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidVoyage))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateVoyage_Success() throws Exception {
        String updatedVoyage = """
                {
                    "destination": "Tokyo Updated",
                    "prixDeBase": 350.0,
                    "categories": []
                }
                """;

        mockMvc.perform(put("/voyages/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedVoyage))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destination").value("Tokyo Updated"))
                .andExpect(jsonPath("$.prixDeBase").value(350.0));
    }

    @Test
    void testUpdateVoyage_NotFound() throws Exception {
        String updatedVoyage = """
                {
                    "destination": "Nonexistent",
                    "prixDeBase": 200.0
                }
                """;

        mockMvc.perform(put("/voyages/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedVoyage))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteVoyage_Success() throws Exception {
        mockMvc.perform(delete("/voyages/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/voyages/1"))
                .andExpect(status().isNotFound());
    }
}


