package org.example.workshop.controller;

import org.example.workshop.dto.cncMachine.*;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.example.workshop.repository.CncMachineRepository;
import org.example.workshop.repository.WorkshopRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CncMachineControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WorkshopRepository workshopRepository;
    @Autowired
    private CncMachineRepository cncMachineRepository;
    @AfterEach
    void tearDown() {
        cncMachineRepository.deleteAll();
        workshopRepository.deleteAll();
    }

    @Test
    public void addCncMachineShouldReturnAddedCncMachine() throws Exception {
        Workshop saved = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(2000)
                        .voltage(230)
                        .build()
        );
        AddCncMachineRequest addCncMachineRequest = AddCncMachineRequest.builder()
                .workshopId(saved.getId())
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        mockMvc.perform(post("/api/cnc-machine/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addCncMachineRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addCncMachineShouldReturnBadRequest() throws Exception {
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
        AddCncMachineRequest addCncMachineRequest = AddCncMachineRequest.builder()
                .workshopId(saved.getId())
                .height(30_000.)
                .width(40_000.)
                .length(30_000.)
                .weight(5000.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        mockMvc.perform(post("/api/cnc-machine/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addCncMachineRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllShouldReturnList() throws Exception {
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
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            cncMachineRepository.save(CncMachine.builder()
                            .workshop(saved)
                            .height(3000. + j)
                            .width(4000.)
                            .length(3000.)
                            .weight(500.)
                            .maxPowerConsumption(300)
                            .voltage(230)
                            .build()
            );
        }
        mockMvc.perform(get("/api/cnc-machine/"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].height").value(3000.0))
                .andExpect(jsonPath("$[1].height").value(4000.0))
                .andExpect(jsonPath("$[2].height").value(5000.0))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cnc-machine/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByIdShouldReturnCncMachine() throws Exception {
        Workshop workshop = workshopRepository.save(
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
        CncMachine saved = null;
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            saved = cncMachineRepository.save(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000. + j)
                    .width(4000.)
                    .length(3000.)
                    .weight(500.)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        mockMvc.perform(get("/api/cnc-machine/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.height").value(5000.0))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/cnc-machine/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCncMachineShouldRemoveEntity() throws Exception {
        Workshop workshop = workshopRepository.save(
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
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        mockMvc.perform(delete("/api/cnc-machine/" + saved.getId()))
                .andExpect(status().isNoContent());
        assertFalse(workshopRepository.findById(saved.getId()).isPresent());
    }

    @Test
    public void deleteCncMachineShouldRemoveNotFound() throws Exception {
        UUID random = UUID.randomUUID();
        mockMvc.perform(delete("/api/cnc-machine/" + random))
                .andExpect(status().isNotFound());
        assertFalse(workshopRepository.findById(random).isPresent());
    }

    @Test
    public void updateCncMachineShouldReturnUpdateEntity() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(10_000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .height(3500.)
                .maxPowerConsumption(350)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.height").value(3500.0))
                .andExpect(jsonPath("$.maxPowerConsumption").value(350))
                .andExpect(jsonPath("$.weight").value(500));
    }

    @Test
    public void updateCncMachineToAnotherWorkshopShouldReturnUpdateEntity() throws Exception {
        Workshop workshopFrom = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(10_000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        Workshop workshopTo = workshopRepository.save(
                Workshop.builder()
                        .code("UA-2025")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(10_000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshopFrom)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .workshopId(workshopTo.getId())
                .height(3500.)
                .maxPowerConsumption(350)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.height").value(3500.0))
                .andExpect(jsonPath("$.maxPowerConsumption").value(350))
                .andExpect(jsonPath("$.weight").value(500))
                .andExpect(jsonPath("$.workshop.id").value(workshopTo.getId().toString()));
    }

    @Test
    public void updateCncMachineShouldReturnBadRequestBecauseOfHeight() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(10_000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .height(35_000.)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCncMachineShouldReturnBadRequestBecauseOfWidth() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(10_000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .width(35_000.)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCncMachineShouldReturnBadRequestBecauseOfLength() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(10_000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .length(35_000.)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCncMachineShouldReturnBadRequestBecauseOfVoltage() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(10_000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .voltage(380)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCncMachineShouldReturnBadRequestBecauseOfFloorLoadCapacity() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(30_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(4000.)
                .length(3000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .weight(20_000.)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCncMachineShouldReturnBadRequestBecauseOfNotAvailableSquare() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(10_000.)
                        .length(10_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(20_000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = null;
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            saved = cncMachineRepository.save(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000.)
                    .width(5000.)
                    .length(5000.)
                    .weight(500.)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .length(10_000.)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCncMachineShouldReturnBadRequestBecauseOfNotAvailablePower() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(20_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(1000)
                        .voltage(230)
                        .build()
        );
        CncMachine saved = null;
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            saved = cncMachineRepository.save(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000.)
                    .width(5000.)
                    .length(5000.)
                    .weight(500.)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        UpdateCncMachineRequest request = UpdateCncMachineRequest.builder()
                .maxPowerConsumption(700)
                .build();
        mockMvc.perform(put("/api/cnc-machine/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getListOfCncMachinesShouldReturnList() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(20_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(2000)
                        .voltage(230)
                        .build()
        );
        for (int i = 0, j = 0; i < 30; i++, j += 10) {
            cncMachineRepository.save(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000. + j)
                    .width(5000. - j)
                    .length(5000. + j)
                    .weight(500. + (double) j / 100)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        List<SortingField> fields = new ArrayList<>();
        fields.add(new SortingField("height", false));
        fields.add(new SortingField("width", true));
        PaginationCncMachineRequest request = new PaginationCncMachineRequest();
        request.setSize(5);
        request.setPage(0);
        request.setWorkshopId(workshop.getId());
        request.setFields(fields);
        mockMvc.perform(post("/api/cnc-machine/_list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(jsonPath("$.list", hasSize(5)))
                .andExpect(jsonPath("$.list[0].height").value(3000.))
                .andExpect(jsonPath("$.list[2].height").value(3020.))
                .andExpect(jsonPath("$.totalPages").value(6))
                .andExpect(status().isOk());
    }

    @Test
    void getListWithoutWorkshopIdShouldReturnBadRequest() throws Exception {
        PaginationCncMachineRequest request = new PaginationCncMachineRequest();
        request.setPage(0);
        request.setSize(5);
        mockMvc.perform(post("/api/cnc-machine/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getListWithoutPageShouldReturnBadRequest() throws Exception {
        PaginationCncMachineRequest request = new PaginationCncMachineRequest();
        request.setWorkshopId(UUID.randomUUID());
        request.setSize(5);
        mockMvc.perform(post("/api/cnc-machine/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getListForNonExistingWorkshopShouldReturnNotFound() throws Exception {
        PaginationCncMachineRequest request = new PaginationCncMachineRequest();
        request.setWorkshopId(UUID.randomUUID());
        request.setPage(0);
        request.setSize(5);

        mockMvc.perform(post("/api/cnc-machine/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getListWithoutSortingFieldsShouldWork() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-7777")
                        .height(10_000.)
                        .width(20_000.)
                        .length(20_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(2000)
                        .voltage(230)
                        .build()
        );
        cncMachineRepository.save(CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build()
        );
        PaginationCncMachineRequest request = new PaginationCncMachineRequest();
        request.setWorkshopId(workshop.getId());
        request.setPage(0);
        request.setSize(5);
        mockMvc.perform(post("/api/cnc-machine/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.list", hasSize(1)));
    }

    @Test
    void getListWithInvalidSortFieldShouldReturnBadRequest() throws Exception {
        PaginationCncMachineRequest request = new PaginationCncMachineRequest();
        request.setWorkshopId(UUID.randomUUID());
        request.setPage(0);
        request.setSize(5);
        request.setFields(List.of(new SortingField("abc", true)));
        mockMvc.perform(post("/api/cnc-machine/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getListWithNegativePageShouldReturnBadRequest() throws Exception {
        PaginationCncMachineRequest request = new PaginationCncMachineRequest();
        request.setWorkshopId(UUID.randomUUID());
        request.setPage(-1);
        request.setSize(5);
        mockMvc.perform(post("/api/cnc-machine/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void generateReportShouldReturnCsvFile() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(20_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(2000)
                        .voltage(230)
                        .build()
        );
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            cncMachineRepository.save(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000. + j)
                    .width(5000. - j)
                    .length(5000. + j)
                    .weight(500. + (double) j / 100)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        SortCncMachineRequest request = new SortCncMachineRequest();
        request.setWorkshopId(workshop.getId());
        request.setFields(List.of());
        MvcResult mvcResult = mockMvc.perform(post("/api/cnc-machine/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(request().asyncStarted())
                .andReturn();
        MvcResult asyncResult = mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"data.csv\""
                ))
                .andExpect(content().contentType("text/csv"))
                .andReturn();
        String csv = asyncResult.getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        assertThat(csv).isNotBlank();
        assertThat(csv).contains("HEIGHT");
        assertThat(csv).contains("3000");
        assertThat(csv).contains("4000");
        assertThat(csv.lines().count()).isEqualTo(4);
    }

    @Test
    void generateReportWithInvalidWorkshopShouldReturnNotFound() throws Exception {
        SortCncMachineRequest request = new SortCncMachineRequest();
        request.setWorkshopId(UUID.randomUUID());
        request.setFields(List.of());
        mockMvc.perform(post("/api/cnc-machine/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void generateReportWithInvalidFieldShouldReturnBadRequest() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(20_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(2000)
                        .voltage(230)
                        .build()
        );
        SortCncMachineRequest request = new SortCncMachineRequest();
        request.setWorkshopId(workshop.getId());
        request.setFields(List.of(new SortingField("abc", true)));
        mockMvc.perform(post("/api/cnc-machine/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void uploadFromFileShouldSaveAllValidRecords() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(20_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(2000)
                        .voltage(230)
                        .build()
        );
        String json = """
        [
          {
            "workshopId": "%s",
            "height": 3000,
            "width": 4000,
            "length": 5000,
            "weight": 500,
            "voltage": 230,
            "maxPowerConsumption": 300
          },
          {
            "workshopId": "%s",
            "height": 3500,
            "width": 4200,
            "length": 5200,
            "weight": 520,
            "voltage": 230,
            "maxPowerConsumption": 320
          }
        ]
        """.formatted(workshop.getId().toString(), workshop.getId().toString());
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "machines.json",
                MediaType.APPLICATION_JSON_VALUE,
                json.getBytes(StandardCharsets.UTF_8)
        );
        mockMvc.perform(multipart("/api/cnc-machine/upload")
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.written").value(2))
                .andExpect(jsonPath("$.unwritten").value(0));

        assertThat(cncMachineRepository.count()).isEqualTo(2);
    }

    @Test
    void uploadFromFileShouldPartiallySaveAndReturnErrors() throws Exception {
        Workshop workshop = workshopRepository.save(
                Workshop.builder()
                        .code("UA-1991")
                        .height(10_000.)
                        .width(20_000.)
                        .length(20_000.)
                        .floorLoadCapacity(1000.)
                        .availablePower(2000)
                        .voltage(230)
                        .build()
        );
        String json = """
        [
          {
            "workshopId": "%s",
            "height": 3000,
            "width": 4000,
            "length": 5000,
            "weight": 500,
            "voltage": 230,
            "maxPowerConsumption": 300
          },
          {
            "workshopId": "%s",
            "height": -100,
            "width": 4000,
            "length": 5000
          }
        ]
        """.formatted(workshop.getId(), workshop.getId());
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "machines.json",
                MediaType.APPLICATION_JSON_VALUE,
                json.getBytes(StandardCharsets.UTF_8)
        );
        mockMvc.perform(multipart("/api/cnc-machine/upload")
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.written").value(1))
                .andExpect(jsonPath("$.unwritten").value(1));
        assertThat(cncMachineRepository.count()).isEqualTo(1);
    }

    @Test
    void uploadFromFileShouldReturnBadRequestBecauseOfNoneWrittenObject() throws Exception {
        String json = """
        [
          {
            "workshopId": 99999,
            "height": 3000,
            "width": 4000
          }
        ]
        """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "machines.json",
                MediaType.APPLICATION_JSON_VALUE,
                json.getBytes(StandardCharsets.UTF_8)
        );
        mockMvc.perform(multipart("/api/cnc-machine/upload")
                        .file(file))
                .andExpect(status().isBadRequest());
    }

    @Test
    void uploadFromFileWithInvalidFieldShouldReturnBadRequest() throws Exception {
        String json = """
        { "abc": 123 }
        """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "machines.json",
                MediaType.APPLICATION_JSON_VALUE,
                json.getBytes(StandardCharsets.UTF_8)
        );
        mockMvc.perform(multipart("/api/cnc-machine/upload")
                        .file(file))
                .andExpect(status().isBadRequest());
    }
}
