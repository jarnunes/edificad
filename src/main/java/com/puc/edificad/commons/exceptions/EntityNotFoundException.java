package com.puc.edificad.commons.exceptions;


import com.puc.edificad.commons.utils.MessageUtils;

import java.io.Serial;

public class EntityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3214011781795087615L;

    public EntityNotFoundException(String msgKey, Object... args) {
        super(MessageUtils.get(msgKey, args));
    }

    public static EntityNotFoundException notFoundForId(){
        return new EntityNotFoundException("entity.not.found");
    }

    public static EntityNotFoundException notFound(){
        return new EntityNotFoundException("entity.not.found");
    }

}
