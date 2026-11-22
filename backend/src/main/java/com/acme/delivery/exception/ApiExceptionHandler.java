package com.acme.delivery.exception;

import com.acme.delivery.transfer.ApiError;
import com.acme.delivery.transfer.ApiResponse;
import com.acme.delivery.transfer.FieldValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            NoSuchElementException exception,
            HttpServletRequest request
    ) {
        ApiError error = ApiError.builder()
                .description(exception.getMessage())
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .apiError(error)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<FieldValidationError> fieldErrors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldValidationError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        ApiError error = ApiError.builder()
                .description("Request validation failed")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .fieldErrors(fieldErrors)
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .apiError(error)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrity(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        if (!isUniqueViolation(exception)) {
            throw exception;
        }

        ApiError error = ApiError.builder()
                .description("A record with the same unique details already exists")
                .httpStatus(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .build();

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .apiError(error)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(response);
    }

    private boolean isUniqueViolation(Throwable exception) {
        Throwable current = exception;

        while (current != null) {
            if (current instanceof SQLException sqlException
                    && "23505".equals(sqlException.getSQLState())) {
                return true;
            }

            current = current.getCause();
        }

        return false;
    }
}