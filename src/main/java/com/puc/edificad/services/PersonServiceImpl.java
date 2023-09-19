package com.puc.edificad.services;

import com.puc.edificad.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {
}
