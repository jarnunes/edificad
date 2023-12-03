package com.puc.edificad.commons.utils;

import com.puc.edificad.model.edsuser.Role;
import com.puc.edificad.model.edsuser.RoleUser;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserUtils {
    private static final String CARACTERES_PERMITIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

    private UserUtils() {
    }

    public static String encode(String rawPassword) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder().matches(rawPassword, encodedPassword);
    }

    public static RoleUser of(Role role) {
        RoleUser ru = new RoleUser();
        ru.setRole(role);
        return ru;
    }

    public static Set<RoleUser> roleUsers() {
        Set<RoleUser> roles = new HashSet<>();

        Arrays.stream(Role.values()).forEach(role -> {
            RoleUser roleUser = new RoleUser();
            roleUser.setRole(role);
            roles.add(roleUser);
        });

        return roles;
    }

    public static Role firstRole(Set<RoleUser> roles) {
        return roles.stream().findFirst().map(RoleUser::getRole).orElse(null);
    }

    public static String gerarSenha() {
        return generateRandomPassword(12);
    }


    public static String generateRandomPassword(int length) {
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CARACTERES_PERMITIDOS.length());
            password.append(CARACTERES_PERMITIDOS.charAt(index));
        }

        return password.toString();
    }
}
