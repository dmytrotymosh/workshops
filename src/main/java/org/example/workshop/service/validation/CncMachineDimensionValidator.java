package org.example.workshop.service.validation;

import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CncMachineDimensionValidator implements CncMachineValidationRule {
    @Override
    public List<CncMachineValueOutOfTheBoundsError> validate(
            List<CncMachine> actualMachines,
            CncMachine currentMachine,
            Workshop workshop
    ) {
        List<CncMachineValueOutOfTheBoundsError> errors = new ArrayList<>();
        if (currentMachine.getWidth() > workshop.getWidth()) {
            errors.add(new CncMachineValueOutOfTheBoundsError(
                    "width", currentMachine.getWidth(), workshop.getWidth())
            );
        }
        if (currentMachine.getHeight() > workshop.getHeight()) {
            errors.add(new CncMachineValueOutOfTheBoundsError(
                    "height", currentMachine.getHeight(), workshop.getHeight())
            );
        }
        if (currentMachine.getLength() > workshop.getLength()) {
            errors.add(new CncMachineValueOutOfTheBoundsError(
                    "length", currentMachine.getLength(), workshop.getLength())
            );
        }
        return errors;
    }
}
