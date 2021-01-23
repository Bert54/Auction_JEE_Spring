package com.ul.gla.auctionbackspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuctionbackspringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionbackspringApplication.class, args);
    }

}
