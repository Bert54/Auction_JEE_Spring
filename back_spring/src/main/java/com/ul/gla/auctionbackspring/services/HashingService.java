package com.ul.gla.auctionbackspring.services;


public interface HashingService {

    public String hashPassword(final String pass);

    public boolean validatePassword(final String userPass, final String storedPass);

}
