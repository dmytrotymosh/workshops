package org.example.workshop.service.validation;

import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CncMachineVoltageValidator implements CncMachineValidationRule {

    @Override
    public List<CncMachineValueOutOfTheBoundsError> validate(
            List<CncMachine> actualMachines,
            CncMachine currentMachine,
            Workshop workshop
    ) {
        List<CncMachineValueOutOfTheBoundsError> errors = new ArrayList<>();
        if (workshop.getVoltage() != currentMachine.getVoltage()) {
            errors.add(new CncMachineValueOutOfTheBoundsError(
                    "voltage", currentMachine.getVoltage(), workshop.getVoltage())
            );
        }
        return errors;
    }
}
