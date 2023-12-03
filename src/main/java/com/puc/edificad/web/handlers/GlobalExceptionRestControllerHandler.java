package com.puc.edificad.web.handlers;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.exceptions.ValidationException;
import com.puc.edificad.web.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@RestControllerAdvice
@CommonsLog
public class GlobalExceptionRestControllerHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleException(ValidationException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createErrorMessage422(e, null, request);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createResponseEntity(e, "Entity not found.", request, HttpStatus.NOT_FOUND);
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
        String substring = Optional.of(e).map(NestedRuntimeException::getRootCause).map(String::valueOf)
                .map(rc -> StringUtils.substringAfter(rc, "Detalhe: ")).orElse(StringUtils.EMPTY);
        return createErrorMessage422(e.getRootCause(), substring, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return createErrorMessage500(e, "Unexpected Exception.", request);
    }

    private ResponseEntity<Object> createErrorMessage500(Exception e, String additionalMessage, HttpServletRequest request){
        log.error(e);
        return createResponseEntity(e, additionalMessage, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> createErrorMessage422(Throwable e, String additionalMessage, HttpServletRequest request){
        return createResponseEntity(e, additionalMessage, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ResponseEntity<Object> createResponseEntity(Throwable e, String cause, HttpServletRequest request,
        HttpStatus status){
        log.error(e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCause(cause);
        errorResponse.setMessageError(e.getMessage());
        errorResponse.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(errorResponse);
    }
}
