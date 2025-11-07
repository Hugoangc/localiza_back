package com.practice.localiza.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEntryException extends RuntimeException {

    public DuplicateEntryException(String resourceName, String fieldValue) {
        super("Duplicate entry: " + resourceName + " with value '" + fieldValue + "' already exists.");
    }
}