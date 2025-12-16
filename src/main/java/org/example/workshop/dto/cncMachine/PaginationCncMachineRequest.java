package org.example.workshop.dto.cncMachine;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
public class PaginationCncMachineRequest extends SortCncMachineRequest {
    @NotNull
    @Min(0)
    private Integer page;
    @NotNull
    @Positive
    private Integer size;
}
