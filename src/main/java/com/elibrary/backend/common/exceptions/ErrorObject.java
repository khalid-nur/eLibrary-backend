package com.elibrary.backend.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents an error object that provides a structured format for error details
 * and for handling exceptions in the system
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
    private String errorCode;
}

