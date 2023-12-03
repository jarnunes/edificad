package com.puc.edificad.services;


import com.puc.edificad.model.BaseEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository<T extends BaseEntity> extends JpaRepositoryImplementation<T, Long> {
}

