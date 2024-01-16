package pl.Exchange_Rate.Exchange_Rate.exceptions.exception;

public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String link){
        super("Invalid url: " + link);
    }
}
