package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dao.OfferRepository;
import com.ul.gla.auctionbackspring.data.Categories;
import com.ul.gla.auctionbackspring.entities.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class MiscServiceImpl implements MiscService{
    private static final int MINREBATE = 5;
    private static final int MAXREBATE = 25;

    @Autowired
    private OfferRepository offerDao;

    @Override
    public Offer getCurrentOffer() {
        return offerDao.find();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void generateNewOffer(){
        this.offerDao.delete();
        String[] categories = Categories.getInstance().getCategories();
        int numCategories = categories.length;
        int selected = new Random().nextInt(numCategories);
        int rebate = new Random().nextInt((MAXREBATE - MINREBATE) + 1) + MINREBATE;
        this.offerDao.save(new Offer(categories[selected], rebate));
    }
}
