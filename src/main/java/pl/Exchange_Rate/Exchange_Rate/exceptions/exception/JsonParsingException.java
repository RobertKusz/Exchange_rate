package pl.Exchange_Rate.Exchange_Rate.exceptions.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonParsingException extends RuntimeException {
    public JsonParsingException(JsonProcessingException e) {
        super("Problem with parsing JSON data :\n" + e);
    }
}
