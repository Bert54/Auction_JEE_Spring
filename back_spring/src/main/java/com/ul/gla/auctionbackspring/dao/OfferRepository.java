package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.Offer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface OfferRepository extends CrudRepository<Offer, Integer> {

    @Query("SELECT o FROM Offer AS o")
    public Offer find();

    public Offer save(Offer offer);

    @Transactional
    @Modifying
    @Query("DELETE FROM Offer o")
    public int delete();

}
