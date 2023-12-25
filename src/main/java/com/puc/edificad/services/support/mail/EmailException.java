package com.puc.edificad.services.support.mail;

import com.puc.edificad.commons.utils.MessageUtils;

public class EmailException extends RuntimeException {

    public EmailException(Exception e) {
        super(MessageUtils.get("email.exception", e));
    }

}
