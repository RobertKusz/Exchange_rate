package pl.Exchange_Rate.Exchange_Rate.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.Exchange_Rate.Exchange_Rate.exceptions.exception.JsonParsingException;

@ControllerAdvice
public class JsonParsingAdvice {

    @ResponseBody
    @ExceptionHandler(JsonParsingException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String JsonParsingHandler(JsonParsingException ex){
        return ex.getMessage();
    }
}
