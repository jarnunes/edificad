package com.puc.edificad.web.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Properties {



    @Value("${spring.mail.username}")
    private String emailOrigin;

    @Value("${spring.application.name}")
    private String applicationName;

}
