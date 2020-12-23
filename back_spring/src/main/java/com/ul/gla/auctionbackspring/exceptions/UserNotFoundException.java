package com.ul.gla.auctionbackspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Username was not found")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("User " + username + " was not found on the server.");
    }

}
