package com.elibrary.backend.common.exceptions;


import com.elibrary.backend.modules.auth.exception.InvalidCredentialsException;
import com.elibrary.backend.modules.auth.exception.UserAlreadyExistsException;
import com.elibrary.backend.modules.checkout.exception.BookAlreadyReturnedException;
import com.elibrary.backend.modules.checkout.exception.LoanOverdueException;
import com.elibrary.backend.modules.checkout.exception.MaximumRenewalsReachedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler for handling various exceptions
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


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

    /**
     * Handles UserAlreadyExistsException with a 409 CONFLICT status
     *
     * @param ex the UserAlreadyExistsException thrown
     * @return an ErrorObject with error details
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorObject handleItemExistsException(UserAlreadyExistsException ex) {
        log.error("Throwing the ItemExistsException from GlobalExceptionHandler {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("DATA_EXISTS")
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    /**
     * Handles MethodArgumentNotValidException with a 400 BAD REQUEST status
     * Exception is thrown when a request contains invalid or missing fields, failing validation requirements
     *
     * @param ex the MethodArgumentNotValidException thrown
     * @return an ErrorObject with validation error details
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        // Extract field-specific validation error messages
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField(), // Field name
                        fieldError -> fieldError.getDefaultMessage() // Error message
                ));

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("statusCode", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", fieldErrors);
        errorResponse.put("timestamp", new Date());
        errorResponse.put("errorCode", "VALIDATION_FAILED");

        log.error("Throwing the MethodArgumentNotValidException from GlobalExceptionHandler {}", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidCredentialsException with 401 UNAUTHORIZED status
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorObject handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.error("Invalid credentials error: {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("INVALID_CREDENTIALS")
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    /**
     * Handles DuplicateResourceException with a 409 CONFLICT status
     *
     * @param ex the DuplicateResourceException thrown
     * @return an ErrorObject with error details
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateResourceException.class)
    public ErrorObject handleDuplicateResourceException(DuplicateResourceException ex) {
        log.error("Duplicate resource conflict: {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("DUPLICATE_RESOURCE")
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    /**
     * Handles LoanOverdueException with a 400 BAD REQUEST status
     *
     * @param ex the LoanOverdueException thrown
     * @return an ErrorObject with error details
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoanOverdueException.class)
    public ErrorObject handleLoanOverdueException(LoanOverdueException ex) {
        log.warn("Loan overdue error: {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("LOAN_OVERDUE") // Specific error code
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    /**
     * Handles MaximumRenewalsReachedException with a 400 BAD REQUEST status
     *
     * @param ex the MaximumRenewalsReachedException thrown
     * @return an ErrorObject with error details
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaximumRenewalsReachedException.class)
    public ErrorObject handleMaximumRenewalsReachedException(MaximumRenewalsReachedException ex) {
        log.warn("Maximum renewals reached: {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("MAXIMUM_RENEWALS_REACHED")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }

    /**
     * Handles BookAlreadyReturnedException with a 400 BAD REQUEST status
     *
     * @param ex the BookAlreadyReturnedException thrown
     * @return an ErrorObject with error details
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ErrorObject handleBookAlreadyReturnedException(BookAlreadyReturnedException ex) {
        log.warn("Book already returned: {}", ex.getMessage());
        return ErrorObject.builder()
                .errorCode("BOOK_ALREADY_RETURNED")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(new Date())
                .build();
    }




}


