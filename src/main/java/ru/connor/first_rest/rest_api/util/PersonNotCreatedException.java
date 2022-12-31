package ru.connor.first_rest.rest_api.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException(String msg){
        super(msg);
    }
}
