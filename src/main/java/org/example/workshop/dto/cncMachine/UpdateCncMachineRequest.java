package org.example.workshop.dto.cncMachine;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCncMachineRequest {
    private UUID workshopId;
    @Size(min = 1)
    private String maker;
    @Size(min = 1)
    private String countryOfOrigin;
    @Size(min = 1)
    private String model;
    @Positive
    private Double length;
    @Positive
    private Double width;
    @Positive
    private Double height;
    @Positive
    private Double weight;
    @Positive
    private Double tableWorkArea;
    @Positive
    private Integer maxPowerConsumption;
    @Positive
    private Integer voltage;
    @Size(min = 1)
    private String commandLanguage;
    @Size(min = 1)
    private String operatingSystem;
}

