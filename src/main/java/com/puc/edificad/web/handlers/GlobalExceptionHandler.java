package com.puc.edificad.web.handlers;

import com.puc.edificad.commons.exceptions.ValidationException;
import com.puc.edificad.web.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleException(ValidationException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createErrorMessage422(e, null, request);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleException(ResponseStatusException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createErrorMessage500(e, "Response error.", request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createErrorMessage422(e, "Constraint violation.", request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleException(DataIntegrityViolationException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createErrorMessage422(e.getRootCause(), null, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createErrorMessage500(e, "Unexpected Exception.", request);
    }

    private ResponseEntity<Object> createErrorMessage500(Exception e, String additionalMessage, HttpServletRequest request){
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessageError(additionalMessage + "Details: "+ e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse);
    }

    private ResponseEntity<Object> createErrorMessage422(Throwable e, String additionalMessage, HttpServletRequest request){
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessageError(StringUtils.trimToEmpty(additionalMessage) + e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(errorResponse);
    }
}
