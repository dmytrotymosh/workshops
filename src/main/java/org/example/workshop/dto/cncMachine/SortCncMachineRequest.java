package org.example.workshop.dto.cncMachine;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SortCncMachineRequest {
    @NotNull
    protected UUID workshopId;
    protected List<SortingField> fields = List.of();
}
