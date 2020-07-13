package com.tnt.aggregator.rest.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * User Defined Exception class
 */
public class ApiException extends RuntimeException {

    private HttpStatus status;
    private String message;
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




    public ApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
