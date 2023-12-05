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


    public static AjaxResponse of() {
        return new AjaxResponse();
    }

}
