package ru.connor.springREST.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.connor.springREST.model.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
