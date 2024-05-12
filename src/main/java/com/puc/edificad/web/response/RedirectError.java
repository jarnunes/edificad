package com.puc.edificad.web.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RedirectError {
    private Object entity;
    private boolean redirected;
}
