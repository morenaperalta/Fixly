package com.femcoders.fixly.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InsufficientPermissionsException extends RuntimeException {
    public InsufficientPermissionsException(String action, String attributeName) {
        super(String.format("Insufficient permissions to %s %s", action, attributeName));
    }

    public InsufficientPermissionsException(String action, String attributeName, String attributeValue) {
        super(String.format("Insufficient permissions to %s %s %s", action, attributeName, attributeValue));
    }
}