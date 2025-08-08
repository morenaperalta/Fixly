package com.femcoders.fixly.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String entityClass, String attributeName, String attributeValue) {
        super(String.format("%s with %s %s already exists", entityClass, attributeName, attributeValue));
    }
}
