package dao;

import configuration.EntityManagerProvider;
import entities.Offer;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
public class OfferDaoImpl implements OfferDao {

    @Inject
    private EntityManagerProvider entityManager;

    @Override
    public Offer find() {
        TypedQuery<Offer> query = this.entityManager.getEntityManager().createQuery(
                "SELECT o FROM Offer AS o", Offer.class);
        List<Offer> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Offer save(Offer offer) {
        this.entityManager.getEntityManager().persist(offer);
        return offer;
    }

    @Override
    public int delete() {
        return this.entityManager.getEntityManager().createQuery("DELETE FROM Offer o")
                .executeUpdate();
    }

}
