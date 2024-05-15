package com.puc.edificad.web.support.cache;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class Cache {
    private final long DEFAULT_EXPIRATION_MINUTES = 5;
    private final Map<String, CacheItem> cacheItems = new HashMap<>();

    private void put(String key, CacheItem item) {
        cacheItems.put(key, item);
    }

    private CacheItem get(String key) {
        return cacheItems.get(key);
    }

    private void remove(String key) {
        cacheItems.forEach((k, v) -> {
            if (v.getExpiration() != null && v.getExpiration().isBefore(LocalDateTime.now())) {
                cacheItems.remove(k);
            }
        });
    }

    public void addItem(Class<?> clazz, String key, Object filter, Object value, long minutesExpiration) {
        CacheItem item = new CacheItem();
        item.setValue(value);
        item.setExpiration(LocalDateTime.now().plusMinutes(minutesExpiration));
        put(createKey(clazz, key, filter), item);
    }

    public void addItem(Class<?> clazz, String key, Object filter,  Object value) {
        addItem(clazz, key, filter, value, DEFAULT_EXPIRATION_MINUTES);
    }

    private String createKey(Class<?> clazz, String key, Object filter) {
        return clazz.getCanonicalName() + "::KEY=" + key + "::FILTER=" + filter;
    }

    public Object getItem(Class<?> clazz, String key, Object filter) {
        CacheItem item = get(createKey(clazz, key, filter));
        if (item != null) {
            return item.getValue();
        }
        return null;
    }


}
