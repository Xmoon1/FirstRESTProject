package ru.connor.springREST.util;

public class PersonDoNotCreatedException extends RuntimeException{
    public  PersonDoNotCreatedException(String msg){
        super(msg);
    }
}
