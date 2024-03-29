package com.puc.edificad.web.controller;


import com.puc.edificad.web.config.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW_CONFIGURACAO')")
public class ConfiguracaoController {

    protected Properties properties;

    @Autowired
    protected void setProperties(Properties propertiesIn){
        properties = propertiesIn;
    }

    @GetMapping
    public String index() {
        return "settings/view";
    }
}
