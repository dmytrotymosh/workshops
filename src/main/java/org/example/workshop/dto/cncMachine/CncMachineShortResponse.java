package org.example.workshop.dto.cncMachine;

import lombok.*;
import org.example.workshop.dto.workshop.WorkshopResponse;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CncMachineShortResponse {
    private UUID id;
    private double height;
    private double width;
    private double length;
    private UUID workshopId;
    private String workshopCode;
    private Instant created;
    private Instant updated;
}
