package com.puc.edificad.services;

import com.puc.edificad.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {

    List<T> findAll();
    Optional<T> findById(Long id);
    void saveAll(List<T> entities);
    T save(T entity);
    T update(T entity);
    void deleteById(Long id);
    void deleteAllById(List<Long> ids);
}
