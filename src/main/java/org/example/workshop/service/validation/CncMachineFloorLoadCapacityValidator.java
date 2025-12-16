package org.example.workshop.service.validation;

import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CncMachineFloorLoadCapacityValidator implements CncMachineValidationRule {

    @Override
    public List<CncMachineValueOutOfTheBoundsError> validate(
            List<CncMachine> actualMachines,
            CncMachine currentMachine,
            Workshop workshop
    ) {
        List<CncMachineValueOutOfTheBoundsError> errors = new ArrayList<>();
        double currentMachineFloorLoadCapacity =
                currentMachine.getWeight() / ((currentMachine.getLength() / 1000) * (currentMachine.getWidth() / 1000));
        if (workshop.getFloorLoadCapacity() < currentMachineFloorLoadCapacity) {
            errors.add(new CncMachineValueOutOfTheBoundsError(
                    "floorLoadCapacity", currentMachineFloorLoadCapacity, workshop.getFloorLoadCapacity())
            );
        }
        return errors;
    }
}
