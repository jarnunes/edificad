package com.puc.edificad.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {

    private String title;
    private long count;
    private String classColor;
    private String icon;
}
