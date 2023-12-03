package com.puc.edificad.commons.utils;


import com.puc.edificad.commons.StaticContextAccessor;
import com.puc.edificad.commons.config.Message;

public class MessageUtils {
    private MessageUtils() {
    }

    public static String get(String messageKey, Object... args) {
        return StaticContextAccessor.getBean(Message.class).get(messageKey, args);
    }

    public static String get(String messageKey) {
        return StaticContextAccessor.getBean(Message.class).get(messageKey);
    }
}
