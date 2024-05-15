package com.puc.edificad.web.support.cache;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CacheItem {
    private LocalDateTime expiration;
    private Object value;

}
