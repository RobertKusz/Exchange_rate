package pl.Exchange_Rate.Exchange_Rate.exceptions.exception;

import java.io.IOException;

public class ConnectionFailException extends RuntimeException {
    public ConnectionFailException(IOException e) {
        super("Connection failed: " + e.getMessage());
    }
}
