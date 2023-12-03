package com.puc.edificad.commons.exceptions;

import com.puc.edificad.commons.utils.MessageUtils;
import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class UserAuthenticationException extends AuthenticationException {
    @Serial
    private static final long serialVersionUID = 3483626010777962318L;

    public UserAuthenticationException(String msgKey, Object... args) {
        super(MessageUtils.get(msgKey, args));
    }


}
