package com.puc.edificad.web.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String cause;
    private String messageError;
    private String path;
}
