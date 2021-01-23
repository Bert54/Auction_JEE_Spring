package com.ul.gla.auctionbackspring.controllers;


import com.ul.gla.auctionbackspring.entities.Offer;
import com.ul.gla.auctionbackspring.services.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auctions/miscs")
public class MiscController {

    @Autowired
    private MiscService miscellaneousService;

    @GetMapping("/offers")
    public Offer getCurrentOffers() {
        return this.miscellaneousService.getCurrentOffer();
    }
}
