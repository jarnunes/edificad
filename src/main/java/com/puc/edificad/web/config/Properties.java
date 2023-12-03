package com.puc.edificad.web.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class Properties {

    @Value("${edificad.web.index.path}")
    private String indexPath;
}
