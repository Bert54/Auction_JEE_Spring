package com.ul.gla.auctionbackspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class IncompleteAddressException extends RuntimeException{
    public IncompleteAddressException(){
        super();
    }
    public IncompleteAddressException(String message, Throwable cause){
        super(message,cause);
    }
    public IncompleteAddressException(String message){
        super(message);
    }
    public IncompleteAddressException(Throwable cause){
        super(cause);
    }

}
