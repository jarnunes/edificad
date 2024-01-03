package com.puc.edificad.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StateItem {

    private State id;
    private String text;
    private String slug;

}