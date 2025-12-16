package org.example.workshop.service.validation;

import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CncMachinePowerValidator implements CncMachineValidationRule {

    @Override
    public List<CncMachineValueOutOfTheBoundsError> validate(
            List<CncMachine> actualMachines,
            CncMachine currentMachine,
            Workshop workshop
    ) {
        List<CncMachineValueOutOfTheBoundsError> errors = new ArrayList<>();
        double currentTotalPowerConsumption = 0.;
        for (CncMachine machine : actualMachines) {
            currentTotalPowerConsumption += machine.getMaxPowerConsumption();
        }
        if (workshop.getAvailablePower() - currentTotalPowerConsumption
                - currentMachine.getMaxPowerConsumption() < 0) {
            errors.add(new CncMachineValueOutOfTheBoundsError(
                    "powerConsumption", currentMachine.getMaxPowerConsumption(),
                    workshop.getAvailablePower() - currentTotalPowerConsumption));
        }
        return errors;
    }
}
