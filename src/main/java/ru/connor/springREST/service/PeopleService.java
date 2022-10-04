package ru.connor.springREST.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.connor.springREST.model.Person;
import ru.connor.springREST.repositories.PeopleRepository;
import ru.connor.springREST.util.PersonNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;


    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }


    @Transactional
    public void save(Person person){
        enrichPerson(person);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public void enrichPerson(Person person){
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWhom("ADMIN");
    }
}
