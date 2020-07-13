package com.tnt.aggregator.rest.model;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Error CLass to set error  messages
 */

public class ApiError {

    private String message;
    private List<String> errors;

    public ApiError(String message, List<String> errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public ApiError(String message, String error) {
        super();
        this.message = message;
        errors = Arrays.asList(error);
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
