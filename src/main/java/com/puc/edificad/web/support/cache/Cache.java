package com.puc.edificad.web.support.cache;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
@CommonsLog
public class Cache {
    private final long DEFAULT_EXPIRATION_MINUTES = 5;
    private final Map<String, CacheItem> cacheItems = new HashMap<>();

    private void put(String key, CacheItem item) {
        cacheItems.put(key, item);
    }

    private CacheItem get(String key) {
        return cacheItems.get(key);
    }

    @Scheduled(fixedRate = 30 * 60 * 1000)
    private void removeExpired() {
        cacheItems.forEach((k, v) -> {
            if (v.getExpiration() != null && v.getExpiration().isBefore(LocalDateTime.now())) {
                cacheItems.remove(k);
            }
        });
    }

    public void addItem(Class<?> clazz, String key, Object filter, Object value, long secondsExpiration) {
        CacheItem item = new CacheItem();
        item.setValue(value);
        item.setExpiration(LocalDateTime.now().plusSeconds(secondsExpiration));
        put(createKey(clazz, key, filter), item);
    }

    public void addItem(Class<?> clazz, String key, Object filter, Object value) {
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

    public Optional<Object> getOptionalItem(Class<?> clazz, String key, Object filter) {
        return Optional.ofNullable(getItem(clazz, key, filter));
    }

    @SuppressWarnings("unchecked")
    public <T, R> R getItem(Class<?> clazz, String key, T filter, Function<T, R> getItemToAddOnCache, long secondsExpiration) {
        Object item = getItem(clazz, key, filter);
        if (item == null) {
            item = getItemToAddOnCache.apply(filter);
            addItem(clazz, key, filter, item, secondsExpiration);
        }
        return (R) item;
    }

    public <T, R> R getItem(Class<?> clazz, String key, T filter, Function<T, R> getItemToAddOnCache) {
        return getItem(clazz, key, filter, getItemToAddOnCache, DEFAULT_EXPIRATION_MINUTES);
    }

    public <T, R> Optional<R> getOptionalItem(Class<?> clazz, String key, T filter, Function<T, R> getItemToAddOnCache) {
        return Optional.ofNullable(getItem(clazz, key, filter, getItemToAddOnCache));
    }

}
