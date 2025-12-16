package org.example.workshop.service.validation;

import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CncMachineSquareValidator implements CncMachineValidationRule {

    @Override
    public List<CncMachineValueOutOfTheBoundsError> validate(
            List<CncMachine> actualMachines,
            CncMachine currentMachine,
            Workshop workshop
    ) {
        List<CncMachineValueOutOfTheBoundsError> errors = new ArrayList<>();
        double totalSquareUnderMachines = 0.;
        for (CncMachine machine : actualMachines) {
            totalSquareUnderMachines += machine.getLength() * machine.getWidth();
        }
        double workshopAvailableSquare = workshop.getWidth() * workshop.getLength() * 0.8 - totalSquareUnderMachines;
        double currentMachineSquare = currentMachine.getWidth() * currentMachine.getLength();
        if ((workshopAvailableSquare - currentMachineSquare) < 0) {
            errors.add(new CncMachineValueOutOfTheBoundsError(
                    "square", currentMachineSquare,
                    workshopAvailableSquare - totalSquareUnderMachines));
        }
        return errors;
    }
}
