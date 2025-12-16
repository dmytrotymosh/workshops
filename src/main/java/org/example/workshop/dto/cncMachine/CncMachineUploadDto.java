package org.example.workshop.dto.cncMachine;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CncMachineUploadDto {
    @NotNull
    private UUID workshopId;
    private String maker;
    private String countryOfOrigin;
    private String model;
    @NotNull
    private Double height;
    @NotNull
    private Double width;
    @NotNull
    private Double length;
    @NotNull
    private Double weight;
    @NotNull
    private Integer voltage;
    @NotNull
    private Integer maxPowerConsumption;
    private Double tableWorkArea;
    private String commandLanguage;
    private String operatingSystem;
}
