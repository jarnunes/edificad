package com.puc.edificad.commons.exceptions;


import com.puc.edificad.commons.utils.MessageUtils;

import java.io.Serial;

public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -977033582391086078L;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String msgKey, Object... args) {
        super(MessageUtils.get(msgKey, args));
    }


    public static ValidationException notFound(){
        return new ValidationException(MessageUtils.get("entity.not.found"));
    }
}
