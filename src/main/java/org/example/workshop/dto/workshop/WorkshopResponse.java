package org.example.workshop.dto.workshop;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class WorkshopResponse {
    private UUID id;
    private String code;
    private double height;
    private double width;
    private double length;
    private double floorLoadCapacity;
    private int voltage;
    private int availablePower;
    private Instant created;
    private Instant updated;
}
