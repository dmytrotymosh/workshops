package org.example.workshop.dto.cncMachine;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCncMachineRequest {
    @NotNull
    private UUID workshopId;
    @NotNull
    @Positive
    private Double length;
    @NotNull
    @Positive
    private Double width;
    @NotNull
    @Positive
    private Double height;
    @NotNull
    @Positive
    private Double weight;
    @NotNull
    @Positive
    private Integer voltage;
    @NotNull
    @Positive
    private Integer maxPowerConsumption;
}
