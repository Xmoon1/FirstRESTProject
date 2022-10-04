package ru.connor.springREST.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.connor.springREST.dto.PersonDTO;
import ru.connor.springREST.model.Person;
import ru.connor.springREST.service.PeopleService;
import ru.connor.springREST.util.PeopleErrorsResponse;
import ru.connor.springREST.util.PersonDoNotCreatedException;
import ru.connor.springREST.util.PersonNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople(){
        return peopleService.findAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPeople(@PathVariable("id") int id){
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        peopleService.delete(id);
        return "Person deleted";
    }

    @PostMapping()
    public String createPerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error: errorList){
                stringBuilder.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new PersonDoNotCreatedException(stringBuilder.toString());
        }

        peopleService.save(convertToPerson(personDTO));
        return "Person added to database successfully";
    }


    @ExceptionHandler
    public ResponseEntity<PeopleErrorsResponse> handler(PersonDoNotCreatedException e){
        PeopleErrorsResponse peopleErrorsResponse = new PeopleErrorsResponse(
                e.getMessage()
        );

        return new ResponseEntity<>(peopleErrorsResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<PeopleErrorsResponse> handler(PersonNotFoundException e){
        PeopleErrorsResponse peopleErrorsResponse = new PeopleErrorsResponse(
                "Person with such id not found"
        );

        return new ResponseEntity<>(peopleErrorsResponse, HttpStatus.NOT_FOUND);
    }

    public Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class); // From Person to PersonDTO

    }

    public PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class); // From Person to PersonDTO

    }
}
