package com.tnt.aggregator.rest.Exception;

import com.tnt.aggregator.rest.model.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.ServiceUnavailableException;

/**
 * Exception handler to handle when 3rd party service is not available
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handle service unavailable exception from calling APIS
     * @param ex
     * @return
     */
    @ExceptionHandler(ServiceUnavailableException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            ServiceUnavailableException ex) {
        ApiError apiError = new ApiError("Service UnAvailable",ex.getMessage());
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }


    /**
     * Handle service unavailable exception from calling APIS
     * @param ex
     * @return
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleApiException(
            ServiceUnavailableException ex) {
        ApiError apiError = new ApiError("INTERNAL_SERVER_ERROR",ex.getMessage());
        apiError.setMessage(ex.getMessage());
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
