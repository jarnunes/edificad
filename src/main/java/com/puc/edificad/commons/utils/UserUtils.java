package com.puc.edificad.commons.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class UserUtils {
    private UserUtils(){
    }

    public static String encode(String rawPassword){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}
