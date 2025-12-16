package org.example.workshop.dto.workshop;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkshopRequest {
    @Size(min = 1, max = 255)
    private String code;
    @Positive
    private Double height;
    @Positive
    private Double width;
    @Positive
    private Double length;
    @Positive
    private Double floorLoadCapacity;
    @Positive
    private Integer voltage;
    @Positive
    private Integer availablePower;
}
