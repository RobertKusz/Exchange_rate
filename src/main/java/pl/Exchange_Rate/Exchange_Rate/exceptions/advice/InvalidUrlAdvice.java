package pl.Exchange_Rate.Exchange_Rate.exceptions.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.Exchange_Rate.Exchange_Rate.exceptions.exception.InvalidUrlException;

@ControllerAdvice
public class InvalidUrlAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidUrlException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String InvalidUrlHandler(InvalidUrlException ex){
        return ex.getMessage();
    }
}