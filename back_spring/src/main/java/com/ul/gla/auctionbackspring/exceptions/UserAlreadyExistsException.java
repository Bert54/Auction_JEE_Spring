package com.ul.gla.auctionbackspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super();
    }
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
