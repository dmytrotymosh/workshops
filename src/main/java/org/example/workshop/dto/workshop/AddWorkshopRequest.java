package org.example.workshop.dto.workshop;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddWorkshopRequest {
    @NotNull
    @Size(min = 1, max = 255)
    private String code;
    @NotNull
    @Positive
    private Double height;
    @NotNull
    @Positive
    private Double width;
    @NotNull
    @Positive
    private Double length;
    @NotNull
    @Positive
    private Double floorLoadCapacity;
    @NotNull
    @Positive
    private Integer voltage;
    @NotNull
    @Positive
    private Integer availablePower;
}
