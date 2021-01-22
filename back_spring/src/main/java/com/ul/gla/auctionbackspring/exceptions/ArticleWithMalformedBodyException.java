package com.ul.gla.auctionbackspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NO_CONTENT)
public class ArticleWithMalformedBodyException extends  RuntimeException{
    public ArticleWithMalformedBodyException(){super();}
    public ArticleWithMalformedBodyException(String message, Throwable cause){super(message,cause);}
    public ArticleWithMalformedBodyException(String message){super(message);}
    public ArticleWithMalformedBodyException(Throwable cause){super(cause);}

}
