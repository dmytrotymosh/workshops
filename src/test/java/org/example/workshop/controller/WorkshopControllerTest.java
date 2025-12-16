package org.example.workshop.controller;

import org.example.workshop.dto.workshop.AddWorkshopRequest;
import org.example.workshop.dto.workshop.UpdateWorkshopRequest;
import org.example.workshop.model.Workshop;
import org.example.workshop.repository.WorkshopRepository;
import org.example.workshop.service.WorkshopService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WorkshopControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WorkshopRepository workshopRepository;
    @AfterEach
    void tearDown() {
        workshopRepository.deleteAll();
    }

    @Test
    public void addWorkshopShouldReturnAddedWorkshop() throws Exception {
        AddWorkshopRequest addWorkshopRequest = AddWorkshopRequest.builder()
                .code("UA-1991")
                .height(30_000.)
                .width(40_000.)
                .length(30_000.)
                .floorLoadCapacity(5000.)
                .availablePower(3000)
                .voltage(230)
                .build();
        mockMvc.perform(post("/api/workshop/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addWorkshopRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addWorkshopShouldReturnBadRequestBecauseOfMissingField() throws Exception {
        AddWorkshopRequest addWorkshopRequest = AddWorkshopRequest.builder()
                .height(30_000.)
                .width(40_000.)
                .length(30_000.)
                .floorLoadCapacity(5000.)
                .availablePower(3000)
                .build();
        mockMvc.perform(post("/api/workshop/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addWorkshopRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWorkshopShouldReturnBadRequestBecauseOfNotValidField() throws Exception {
        AddWorkshopRequest addWorkshopRequest = AddWorkshopRequest.builder()
                .height(30_000.)
                .width(40_000.)
                .length(-30_000.)
                .floorLoadCapacity(5000.)
                .availablePower(3000)
                .voltage(230)
                .build();
        mockMvc.perform(post("/api/workshop/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addWorkshopRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWorkshopShouldReturnBadRequestBecauseOfUniquenessConstraint() throws Exception {
        List<AddWorkshopRequest> requests = new ArrayList<>();
        for (int i = 0, j = 0; i < 3; i++, j += 10000) {
            AddWorkshopRequest request = new AddWorkshopRequest();
            request.setCode("UA-1991");
            request.setHeight(30000. + j);
            request.setWidth(40000.);
            request.setLength(30000.);
            request.setFloorLoadCapacity(5000.);
            request.setAvailablePower(3000);
            request.setVoltage(230);
            requests.add(request);
        }
        mockMvc.perform(post("/api/workshop/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests.get(0))))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/workshop/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests.get(1))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllShouldReturnList() throws Exception {
        for (int i = 0, j = 0; i < 3; i++, j += 10000) {
            Workshop entity = new Workshop();
            entity.setCode("UA-" + j);
            entity.setHeight(30000. + j);
            entity.setWidth(40000.);
            entity.setLength(30000.);
            entity.setFloorLoadCapacity(5000.);
            entity.setAvailablePower(3000);
            entity.setVoltage(230);
            workshopRepository.save(entity);
        }
        mockMvc.perform(get("/api/workshop/"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].height").value(30000.0))
                .andExpect(jsonPath("$[1].height").value(40000.0))
                .andExpect(jsonPath("$[2].height").value(50000.0))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/workshop/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWorkshopShouldReturnUpdateEntity() throws Exception {
        Workshop saved = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10000.)
                        .width(20000.)
                        .length(30000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(200)
                        .voltage(220)
                        .build()
        );
        UpdateWorkshopRequest request = UpdateWorkshopRequest.builder()
                .height(11111.)
                .voltage(380)
                .build();
        mockMvc.perform(put("/api/workshop/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.height").value(11111.0));
    }

    @Test
    public void updateWorkshopShouldReturnBadRequestBecauseOfNotValidField() throws Exception {
        Workshop saved = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10000.)
                        .width(20000.)
                        .length(30000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(200)
                        .voltage(220)
                        .build()
        );
        UpdateWorkshopRequest request = UpdateWorkshopRequest.builder()
                .height(11111.)
                .width(22222.)
                .length(-33333.)
                .floorLoadCapacity(4444.)
                .availablePower(555)
                .voltage(380)
                .build();
        mockMvc.perform(put("/api/workshop/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWorkshopShouldReturnBadRequestBecauseOfUniquenessConstraint() throws Exception {
        workshopRepository.save(
                Workshop.builder()
                        .code("UA-2025")
                        .height(10000.)
                        .width(20000.)
                        .length(30000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(200)
                        .voltage(220)
                        .build()
        );
        Workshop saved = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10000.)
                        .width(20000.)
                        .length(30000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(200)
                        .voltage(220)
                        .build()
        );
        UpdateWorkshopRequest request = UpdateWorkshopRequest.builder()
                .code("UA-2025")
                .height(11111.)
                .width(22222.)
                .length(-33333.)
                .floorLoadCapacity(4444.)
                .availablePower(555)
                .voltage(380)
                .build();
        mockMvc.perform(put("/api/workshop/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWorkshopShouldReturnNotFound() throws Exception {
        UpdateWorkshopRequest request = UpdateWorkshopRequest.builder()
                .voltage(380)
                .build();
        mockMvc.perform(put("/api/workshop/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWorkshopShouldRemoveEntity() throws Exception {
        Workshop saved = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10000.)
                        .width(20000.)
                        .length(30000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(200)
                        .voltage(220)
                        .build()
        );
        mockMvc.perform(delete("/api/workshop/" + saved.getId()))
                .andExpect(status().isNoContent());
        assertFalse(workshopRepository.findById(saved.getId()).isPresent());
    }

    @Test
    public void deleteWorkshopShouldReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/workshop/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
