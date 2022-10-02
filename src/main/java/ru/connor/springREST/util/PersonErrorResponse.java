package ru.connor.springREST.util;

public class PersonErrorResponse {
    private String message;
    private long timestamp;

    public PersonErrorResponse(String message, long timestamp) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
