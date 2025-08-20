package com.femcoders.fixly.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest req) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String controllerPath = String.valueOf(req.getRequestURL());
        ErrorResponse errorResponse = new ErrorResponse(status, exception.getMessage(), controllerPath);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception, HttpServletRequest req) {
        HttpStatus status = HttpStatus.CONFLICT;
        String controllerPath = String.valueOf(req.getRequestURL());
        ErrorResponse errorResponse = new ErrorResponse(status, exception.getMessage(), controllerPath);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
