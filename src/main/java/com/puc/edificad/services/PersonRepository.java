package com.puc.edificad.services;

import com.puc.edificad.model.Person;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends BaseRepository<Person> {


    List<Person> findAllByNomeOrEmailOrderById(@Param("nome") String nome, @Param("email") String email);
}
