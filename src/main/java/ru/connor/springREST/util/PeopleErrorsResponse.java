package ru.connor.springREST.util;


public class PeopleErrorsResponse {
    private String message;

    public PeopleErrorsResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
