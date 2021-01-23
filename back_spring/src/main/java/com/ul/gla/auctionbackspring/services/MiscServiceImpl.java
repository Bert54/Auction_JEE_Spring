package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dao.OfferRepository;
import com.ul.gla.auctionbackspring.entities.Offer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MiscServiceImpl implements MiscService{
    OfferRepository offerDao;

    @Override
    public Offer getCurrentOffer() {
        return offerDao.find();
    }
//    @Scheduled(cron = "0 * * * ?")
    public void generateNewOffer(){
    }
}
