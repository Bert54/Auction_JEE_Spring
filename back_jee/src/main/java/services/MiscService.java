package services;

import entities.Offer;

import javax.ejb.Local;

@Local
public interface MiscService {

    public Offer getCurrentOffer();

}
