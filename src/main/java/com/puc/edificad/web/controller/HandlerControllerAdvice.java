package com.puc.edificad.web.controller;

import com.jnunes.spgauth.services.TokenService;
import com.jnunes.spgauth.web.support.UserHelper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

import static org.springframework.web.util.WebUtils.getCookie;

@ControllerAdvice
public class HandlerControllerAdvice {
    private static final String TOKEN = "tokenUser";

    private UserHelper userHelper;
    private TokenService tokenService;

    @Autowired
    public void setUserHelper(UserHelper helper) {
        this.userHelper = helper;
    }

    @Autowired
    public void setTokenService(TokenService tokenServiceIn) {
        this.tokenService = tokenServiceIn;
    }


}
