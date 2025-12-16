package org.example.workshop.dto.cncMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CncMachineValueOutOfTheBoundsError {
    private String field;
    private double actualValue;
    private double workshopValue;
}
