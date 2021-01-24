package com.ul.gla.auctionbackspring.services;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

public interface RMQCommunicationService {

    public int sendOrder(String id);

}
