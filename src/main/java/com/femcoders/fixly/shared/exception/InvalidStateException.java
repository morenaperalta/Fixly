package com.femcoders.fixly.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidStateException extends RuntimeException{
    public InvalidStateException(String entityClass, String state){
        super(String.format("Resource %s is in invalid state: %s", entityClass, state));
    }

}
