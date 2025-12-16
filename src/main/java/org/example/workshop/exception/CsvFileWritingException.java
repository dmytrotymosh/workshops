package org.example.workshop.exception;

public class CsvFileWritingException extends RuntimeException {
    public CsvFileWritingException(String message) {
        super("Error caused while writing to CSV file: " + message);
    }
}
