package ru.connor.first_rest.rest_api.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonErrorResponse {
    private String message;
    private long timestamp;
}
