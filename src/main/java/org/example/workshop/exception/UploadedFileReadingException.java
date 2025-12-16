package org.example.workshop.exception;

public class UploadedFileReadingException extends RuntimeException {
    public UploadedFileReadingException(String message) {
        super("Error caused while reading uploaded file: " + message);
    }
}
