package org.example.workshop.exception;

public class UniquenessException extends RuntimeException {
    public UniquenessException(String message) {
        super(message);
    }
}
