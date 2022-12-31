package ru.connor.first_rest.rest_api.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import ru.connor.first_rest.rest_api.dto.PersonDTO;
import ru.connor.first_rest.rest_api.model.Person;
import ru.connor.first_rest.rest_api.service.PeopleService;
import ru.connor.first_rest.rest_api.util.PeopleNotFountException;
import ru.connor.first_rest.rest_api.util.PersonErrorResponse;
import ru.connor.first_rest.rest_api.util.PersonNotCreatedException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @GetMapping()
    public List<PersonDTO> allPeople(){
        return peopleService.getAllPeople().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO onePerson(@PathVariable int id){
        return convertToPersonDTO(peopleService.getPersonById(id));
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handlerException(PeopleNotFountException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person with this id was not founded", System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError: fieldErrors){
                errorMessage.append(fieldError.getField()).append(" - ").append(fieldError.getDefaultMessage()).append(";");
            }

            throw new PersonNotCreatedException(errorMessage.toString());

        }
        peopleService.savePerson(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable @RequestBody int id){
        if (peopleService.getPersonById(id) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        peopleService.deletePersonById(id);
        return ResponseEntity.ok("Person with id " + id + " deleted");
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}