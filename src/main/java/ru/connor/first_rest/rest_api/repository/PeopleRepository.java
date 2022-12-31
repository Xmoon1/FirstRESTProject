package ru.connor.first_rest.rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.connor.first_rest.rest_api.model.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
