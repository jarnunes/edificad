package com.puc.edificad.services;

import com.jnunes.core.model.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {

    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    Page<T> findAll(Example<T> example, Pageable pageable);
    Page<T> findAll(String searchValue, Pageable pageable);

    Optional<T> getEntityWithSearchAttrs(final String searchValue);

    Optional<T> findById(Long id);
    void saveAll(List<T> entities);
    T save(T entity);
    T update(T entity);

    void delete(T entity);
    void deleteById(Long id);
    void deleteAllById(List<Long> ids);

    Long count();
}
