package org.example.workshop.exception;

public class UnknownFieldException extends RuntimeException {
    public UnknownFieldException(String message) {
        super(message);
    }
}
