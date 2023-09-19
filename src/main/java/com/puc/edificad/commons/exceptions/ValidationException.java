package com.puc.edificad.commons.exceptions;


import com.puc.edificad.commons.utils.MessageUtils;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String msgKey, Object... args) {
        super(MessageUtils.get(msgKey, args));
    }

}
