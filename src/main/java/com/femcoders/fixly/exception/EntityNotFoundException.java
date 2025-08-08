package com.femcoders.fixly.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityClass, String attributeName, String attributeValue) {
        super(String.format("%s with %s %s was not found", entityClass, attributeName, attributeValue));
    }
}
