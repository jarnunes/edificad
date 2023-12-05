package com.puc.edificad.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String cause;

    @JsonProperty("error_message")
    private String messageError;
    private String path;
}
