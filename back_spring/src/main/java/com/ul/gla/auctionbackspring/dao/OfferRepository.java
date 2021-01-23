package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.Offer;
import org.springframework.data.jpa.repository.Query;

public interface OfferRepository {

    @Query("SELECT o FROM Offer AS o")
    public Offer find();

    public Offer save(Offer offer);

    @Query("DELETE FROM Offer o")
    public int delete();

}
