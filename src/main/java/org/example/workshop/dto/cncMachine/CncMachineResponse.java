package org.example.workshop.dto.cncMachine;

import lombok.Getter;
import lombok.Setter;
import org.example.workshop.dto.workshop.WorkshopResponse;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class CncMachineResponse {
    private UUID id;
    private String maker;
    private String countryOfOrigin;
    private String model;
    private double length;
    private double width;
    private double height;
    private double weight;
    private Double tableWorkArea;
    private int maxPowerConsumption;
    private int voltage;
    private String commandLanguage;
    private String operatingSystem;
    private Instant created;
    private Instant updated;
    private WorkshopResponse workshop;
}
