package com.puc.edificad.web.api;

import com.puc.edificad.commons.config.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {

    @Autowired
    @Lazy
    protected Message msg;
}
