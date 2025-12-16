package org.example.workshop.service;

import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.example.workshop.service.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CncMachineValidationTest {
    @Test
    public void testCncMachineSquareValidatorShouldReturnEmptyErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(1000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            actualCncMachines.add(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000.)
                    .width(5000.)
                    .length(5000.)
                    .weight(500.)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineSquareValidator cncMachineSquareValidator = new CncMachineSquareValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineSquareValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testCncMachineSquareValidatorShouldReturnErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(10_000.)
                .length(10_000.)
                .floorLoadCapacity(1000.)
                .availablePower(1000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            actualCncMachines.add(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000.)
                    .width(5000.)
                    .length(5000.)
                    .weight(500.)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(7000.)
                .length(7000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineSquareValidator cncMachineSquareValidator = new CncMachineSquareValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineSquareValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertFalse(errors.isEmpty());
    }

    @Test
    public void testCncMachinePowerValidatorShouldReturnEmptyErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            actualCncMachines.add(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000.)
                    .width(5000.)
                    .length(5000.)
                    .weight(500.)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachinePowerValidator cncMachinePowerValidator = new CncMachinePowerValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachinePowerValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testCncMachinePowerValidatorShouldReturnErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(1000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        for (int i = 0, j = 0; i < 3; i++, j += 1000) {
            actualCncMachines.add(CncMachine.builder()
                    .workshop(workshop)
                    .height(3000.)
                    .width(5000.)
                    .length(5000.)
                    .weight(500.)
                    .maxPowerConsumption(300)
                    .voltage(230)
                    .build()
            );
        }
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachinePowerValidator cncMachinePowerValidator = new CncMachinePowerValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachinePowerValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertFalse(errors.isEmpty());
    }

    @Test
    public void testCncMachineVoltageValidatorShouldReturnEmptyErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineVoltageValidator cncMachineVoltageValidator = new CncMachineVoltageValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineVoltageValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testCncMachineVoltageValidatorShouldReturnErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(380)
                .build();
        CncMachineVoltageValidator cncMachineVoltageValidator = new CncMachineVoltageValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineVoltageValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertFalse(errors.isEmpty());
    }

    @Test
    public void testCncMachineFloorLoadCapacityValidatorShouldReturnEmptyErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineFloorLoadCapacityValidator cncMachineFloorLoadCapacityValidator =
                new CncMachineFloorLoadCapacityValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineFloorLoadCapacityValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testCncMachineFloorLoadCapacityValidatorShouldReturnErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(300.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(3000.)
                .width(5000.)
                .length(5000.)
                .weight(10_000.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineFloorLoadCapacityValidator cncMachineFloorLoadCapacityValidator =
                new CncMachineFloorLoadCapacityValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineFloorLoadCapacityValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertFalse(errors.isEmpty());
    }

    @Test
    public void testCncMachineDimensionValidatorShouldReturnEmptyErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(5_000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineDimensionValidator cncMachineDimensionValidator =
                new CncMachineDimensionValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineDimensionValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertTrue(errors.isEmpty());
    }

    @Test
    public void testCncMachineHeightValidatorShouldReturnErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(50_000.)
                .width(5000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineDimensionValidator cncMachineDimensionValidator =
                new CncMachineDimensionValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineDimensionValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertFalse(errors.isEmpty());
    }

    @Test
    public void testCncMachineWidthValidatorShouldReturnErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(5000.)
                .width(50_000.)
                .length(5000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineDimensionValidator cncMachineDimensionValidator =
                new CncMachineDimensionValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineDimensionValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertFalse(errors.isEmpty());
    }

    @Test
    public void testCncMachineLengthValidatorShouldReturnErrorsList() {
        Workshop workshop = Workshop.builder()
                .height(10_000.)
                .width(20_000.)
                .length(20_000.)
                .floorLoadCapacity(1000.)
                .availablePower(2000)
                .voltage(230)
                .build();
        List<CncMachine> actualCncMachines = new ArrayList<>();
        CncMachine currentMachine = CncMachine.builder()
                .workshop(workshop)
                .height(5000.)
                .width(5000.)
                .length(50_000.)
                .weight(500.)
                .maxPowerConsumption(300)
                .voltage(230)
                .build();
        CncMachineDimensionValidator cncMachineDimensionValidator =
                new CncMachineDimensionValidator();
        List<CncMachineValueOutOfTheBoundsError> errors = cncMachineDimensionValidator.validate(
                actualCncMachines,
                currentMachine,
                workshop
        );
        Assertions.assertFalse(errors.isEmpty());
    }
}
