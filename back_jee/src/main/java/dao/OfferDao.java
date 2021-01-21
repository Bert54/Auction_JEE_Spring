package dao;

import entities.Offer;

import javax.ejb.Local;

@Local
public interface OfferDao {

    public Offer find();

    public Offer save(Offer offer);

    public int delete();

}
