package org.example.model.exception;

public class ApiInputException extends RuntimeException {

    public ApiInputException() {
        super();
    }

    public ApiInputException(String message) {
        super(message);
    }

    public ApiInputException(String message, Throwable cause) {
        super(message, cause);
    }

}
