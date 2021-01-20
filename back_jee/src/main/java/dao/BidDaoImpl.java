package dao;

import configuration.EntityManagerProvider;
import entities.Article;
import entities.Bid;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
public class BidDaoImpl implements BidDao {

    @Inject
    private EntityManagerProvider entityManager;

    @Override
    public Bid save(Bid bid) {
        this.entityManager.getEntityManager().persist(bid);
        return bid;
    }

    public Bid find(String bidder, long articleId) {
        TypedQuery<Bid> query = this.entityManager.getEntityManager().createQuery(
                "SELECT b FROM Bid AS b WHERE b.bidder = :bidder AND b.articleId = :articleId", Bid.class)
                .setParameter("bidder", bidder)
                .setParameter("articleId", articleId);
        List<Bid> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

}
