package dao;

import entities.Bid;

import javax.ejb.Local;

@Local
public interface BidDao {

    public Bid save(Bid bid);

    public Bid find(String bidder, long articleId);

    public int delete(long articleId);

}
