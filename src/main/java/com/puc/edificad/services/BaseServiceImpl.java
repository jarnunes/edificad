package com.puc.edificad.services;

import com.jnunes.core.commons.utils.ValidationUtils;
import com.jnunes.core.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private BaseRepository<T> repository;

    @Autowired
    public void setRepository(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public Page<T> findAll(String searchValue, Pageable pageable) {
        return getEntityWithSearchAttrs(searchValue).map(it -> this.findAll(Example.of(it, getExampleMatcher()), pageable))
                .orElse(Page.empty());
    }

    @Override
    public Optional<T> getEntityWithSearchAttrs(final String searchValue){
        return Optional.empty();
    }

    protected ExampleMatcher getExampleMatcher(){
        return ExampleMatcher.matchingAny().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase().withIgnoreNullValues();
    }


    @Override
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void saveAll(List<T> entities) {
        entities.forEach(this::save);
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(T entity) {
        ValidationUtils.validateNonNull(entity::getId, "entity.id.not.null");
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllById(List<Long> ids) {
        repository.deleteAllById(ids);
    }


    @Override
    public Long count() {
        return repository.count();
    }
}
