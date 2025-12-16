package org.example.workshop.service.validation;

import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;
import org.example.workshop.model.CncMachine;
import org.example.workshop.model.Workshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CncMachineValidator {
    private static List<CncMachineValidationRule> rules;
    @Autowired
    public CncMachineValidator(
            CncMachineDimensionValidator dimensionValidator,
            CncMachineSquareValidator squareValidator,
            CncMachinePowerValidator powerValidator,
            CncMachineVoltageValidator voltageValidator,
            CncMachineFloorLoadCapacityValidator floorLoadCapacityValidator
    ) {
        rules = List.of(dimensionValidator, squareValidator, powerValidator,
                voltageValidator, floorLoadCapacityValidator);
    }

    public static List<CncMachineValueOutOfTheBoundsError> validate(
            List<CncMachine> actualMachines,
            CncMachine currentMachine,
            Workshop workshop) {
        List<CncMachineValueOutOfTheBoundsError> result = new ArrayList<>();
        for (CncMachineValidationRule rule : rules) {
            result.addAll(rule.validate(actualMachines, currentMachine, workshop));
        }
        return result;
    }
}

