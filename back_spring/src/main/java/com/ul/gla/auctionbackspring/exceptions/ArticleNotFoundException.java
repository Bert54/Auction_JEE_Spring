package com.ul.gla.auctionbackspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException() {
        super();
    }
    public ArticleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
    public ArticleNotFoundException(Throwable cause) {
        super(cause);
    }
}
