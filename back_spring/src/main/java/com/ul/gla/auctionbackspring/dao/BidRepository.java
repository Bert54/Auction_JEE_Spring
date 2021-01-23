package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.Bid;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface BidRepository extends CrudRepository<Bid,Integer> {

    Bid save(Bid bid);

    @Query("SELECT b FROM Bid AS b WHERE b.bidder = :bidder AND b.articleId = :articleId")
    Bid find(String bidder, long articleId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Bid b WHERE b.articleId = :id")
    int delete(long articleId);

}
