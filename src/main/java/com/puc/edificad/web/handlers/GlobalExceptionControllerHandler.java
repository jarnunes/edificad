package com.puc.edificad.web.handlers;


import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.exceptions.ValidationException;
import com.puc.edificad.commons.utils.ExceptionUtils;
import com.puc.edificad.web.support.MessagesAlert;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionControllerHandler {

    private static final String MESSAGES_KEY = "messages";
    @ExceptionHandler(ValidationException.class)
    public String handleException(ValidationException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute(MESSAGES_KEY,  new MessagesAlert(e));
        return getRedirect(request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleException(EntityNotFoundException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        MessagesAlert messagesAlert = new MessagesAlert();
        messagesAlert.addError("Entity not found." + ExceptionUtils.getRootCause(e));
        redirectAttributes.addFlashAttribute(MESSAGES_KEY,  messagesAlert);
        return getRedirect(request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleException(DataIntegrityViolationException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        MessagesAlert messagesAlert = new MessagesAlert();
        messagesAlert.addError("Violação de constraint. " + ExceptionUtils.getRootCause(e));
        redirectAttributes.addFlashAttribute(MESSAGES_KEY,  messagesAlert);
        return getRedirect(request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleException(ConstraintViolationException e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        MessagesAlert messagesAlert = new MessagesAlert();
        messagesAlert.addError("Violação de constraint. " + ExceptionUtils.getRootCause(e));
        redirectAttributes.addFlashAttribute(MESSAGES_KEY,  messagesAlert);
        return getRedirect(request);
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        MessagesAlert alert = new MessagesAlert();
        alert.addError("Unexpected error: " + e.getMessage());
        redirectAttributes.addFlashAttribute(MESSAGES_KEY,  alert);
        return getRedirect(request);
    }

    private String getRedirect(HttpServletRequest request){
        String referer = Optional.ofNullable(request.getHeader("Referer")).orElse("/error");
        return "redirect:" + referer;
    }
}
