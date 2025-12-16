package org.example.workshop.exception;

import lombok.Getter;
import org.example.workshop.dto.cncMachine.CncMachineValueOutOfTheBoundsError;

import java.util.List;

@Getter
public class CncMachineValueOutOfTheBoundsException extends RuntimeException {
    private final List<CncMachineValueOutOfTheBoundsError> errors;
    public CncMachineValueOutOfTheBoundsException(List<CncMachineValueOutOfTheBoundsError> errors) {
        super("CNC Machine doesn't fit the workshop");
        this.errors = errors;
    }
}
