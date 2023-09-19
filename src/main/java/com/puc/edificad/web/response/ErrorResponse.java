package com.puc.edificad.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    @JsonProperty("message_error")
    private String messageError;

    @JsonProperty("path")
    private String path;

}
