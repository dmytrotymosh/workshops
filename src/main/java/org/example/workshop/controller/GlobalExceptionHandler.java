package org.example.workshop.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.workshop.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CncMachineValueOutOfTheBoundsException.class)
    public ResponseEntity<Object> handleCncMachineValueOutOfTheBoundsException(
            CncMachineValueOutOfTheBoundsException e) {
        log.warn(e.getMessage());
        Map<String, Object> body = new HashMap<>();
        body.put("message", e.getMessage());
        body.put("errors", e.getErrors());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UploadedFileReadingException.class)
    public ResponseEntity<Object> handleUploadedFileReadingException(UploadedFileReadingException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownFieldException.class)
    public ResponseEntity<Object> handleUnknownFieldException(UnknownFieldException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CsvFileWritingException.class)
    public ResponseEntity<Object> handleCsvFileWritingException(CsvFileWritingException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UniquenessException.class)
    public ResponseEntity<Object> handleUniquenessException(UniquenessException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
