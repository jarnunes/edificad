package com.puc.edificad.web.support;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AjaxResponse {

    private int statusCode;
    private List<String> messages = new ArrayList<>();

    private Object data;

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public void setStatusCode(StatusCode statusCodeIn){
        this.statusCode = statusCodeIn.ordinal();
    }

    public static AjaxResponse of() {
        return new AjaxResponse();
    }

    public AjaxResponse success() {
        setStatusCode(StatusCode.SUCCESS);
        return this;
    }

    public AjaxResponse error() {
        setStatusCode(StatusCode.ERROR);
        return this;
    }

    public AjaxResponse newMessage(String message) {
        this.addMessage(message);
        return this;
    }


}
