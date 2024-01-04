package com.puc.edificad.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutocompleteDto {
    private Object id;
    private String text;
    private String slug;
}
