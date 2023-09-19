package com.puc.edificad.web.handlers;

import com.puc.edificad.commons.exceptions.ValidationException;
import com.puc.edificad.web.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
        return ResponseEntity.internalServerError()
            .body(createErrorMessage(e, "Validation Exception", request));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleException(ResponseStatusException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return ResponseEntity.internalServerError()
            .body(createErrorMessage(e, "Response error.", request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        return ResponseEntity.internalServerError()
            .body(createErrorMessage(e, "Unexpected Exception.", request));
    }

    private ResponseEntity<Object> createErrorMessage(Exception e, String additionalMessage, HttpServletRequest request){
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessageError(additionalMessage + "Details: "+ e.getMessage());
        errorResponse.setPath(request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
