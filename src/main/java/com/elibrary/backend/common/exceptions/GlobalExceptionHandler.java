package com.elibrary.backend.common.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Global exception handler for handling various exceptions
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * Handles general exceptions with a 500 status
     *
     * @param ex the Exception thrown
     * @return an ErrorObject with error details
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorObject handleGeneralException(Exception ex) {
        log.error("Throwing the Exception from GlobalExceptionHandler {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("UNEXPECTED_ERROR")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }


    /**
     * Handles resource not found exceptions with a 404 NOT FOUND status
     *
     * @param ex the ResourceNotFoundExceptions thrown
     * @return an ErrorObject with error details
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundExceptions.class)
    public ErrorObject handleResourceNotFoundException(ResourceNotFoundExceptions ex) {
        log.error("Throwing the ResourceNotFoundException from GlobalExceptionHandler {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("DATA_NOT_FOUND")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }



}


