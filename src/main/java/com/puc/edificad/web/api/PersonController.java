package com.puc.edificad.web.api;


import com.puc.edificad.model.Person;
import com.puc.edificad.services.PersonRepository;
import com.puc.edificad.services.PersonService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.puc.edificad.web.response.BaseResponse.of;

@RestController
@RequestMapping("/demo/person")
@CommonsLog
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    // TODO: usando Repository direto no controller apenas para teste.
    //  Para manter as responsabilidades isoladas, o controller vai chamar apenas os service.
    private PersonRepository repository;

    @GetMapping("/list")
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> personList = personService.findAll();
        if(!personList.isEmpty())
            return of(personList);

        populateDatabase();
        return of(personService.findAll());
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        return of(personService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Person>> searchBy(@RequestParam("name") String name,
        @RequestParam("email") String email) {

        List<Person> personList = repository.findAllByNomeOrEmailOrderById(name, email);
        return of(personList);
    }

    private void populateDatabase() {
        IntStream.range(0, 10).mapToObj(this::createDummyPerson).forEach(repository::save);
    }

    private Person createDummyPerson(int index) {
        Person person = new Person();
        person.setNome("Persona " + index);
        person.setEmail("email" + index + ".edificad@gmail.com");
        return person;
    }
}
