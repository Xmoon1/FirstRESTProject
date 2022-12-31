package ru.connor.first_rest.rest_api.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.connor.first_rest.rest_api.model.Person;
import ru.connor.first_rest.rest_api.repository.PeopleRepository;
import ru.connor.first_rest.rest_api.util.PeopleNotFountException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public List<Person> getAllPeople(){
        return peopleRepository.findAll();
    }

    public Person getPersonById(int id){
        Optional<Person> foundedPerson = peopleRepository.findById(id);
        return foundedPerson.orElseThrow(PeopleNotFountException::new); //TODO
    }

    @Transactional
    public void savePerson(Person person){
        enrichPerson(person);
        peopleRepository.save(person);
    }

    public void enrichPerson(Person person){
        person.setCreated_at(LocalDateTime.now());
        person.setUpdated_at(LocalDateTime.now());
    }
    @Transactional
    public void deletePersonById(int id){
        peopleRepository.deleteById(id);
    }
}
