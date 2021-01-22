package dao;

import configuration.EntityManagerProvider;
import dto.BidArticleDto;
import entities.Article;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
public class ArticleDaoImpl implements ArticleDao {

    @Inject
    private EntityManagerProvider entityManager;

    @Override
    public List<Article> findAll(long timestamp) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE a.endingDate > :timestamp", Article.class)
                .setParameter("timestamp", timestamp);
        return query.getResultList();
    }

    @Override
    public List<Article> findAll(String username) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE a.seller = :username", Article.class)
                .setParameter("username", username);
        return query.getResultList();
    }

    public Article find(long id) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE a.id = :id", Article.class)
                .setParameter("id", id);
        List<Article> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<Article> find(String name, long timestamp) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE LOWER(a.name) LIKE LOWER(:name) AND a.endingDate > :timestamp", Article.class)
                .setParameter("name", "%" + name + "%")
                .setParameter("timestamp", timestamp);
        return query.getResultList();
    }

    @Override
    public List<Article> find(long timestamp, String category) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE LOWER(a.categories) LIKE LOWER(:category) AND a.endingDate > :timestamp", Article.class)
                .setParameter("category", "%" + category + "%")
                .setParameter("timestamp", timestamp);
        return query.getResultList();
    }

    @Override
    public List<Article> find(String username) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article a JOIN Bid b ON a.id = b.articleId WHERE b.bidder = :username ", Article.class)
                .setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public List<Article> findFinished(String username, long timestamp) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE a.lastBidder = :username AND a.endingDate <= :timestamp", Article.class)
                .setParameter("username", username)
                .setParameter("timestamp", timestamp);
        return query.getResultList();
    }

    @Override
    public Article save(Article article) {
        this.entityManager.getEntityManager().persist(article);
        return article;
    }

    @Override
    public int delete(long id) {
        return this.entityManager.getEntityManager().createQuery("DELETE FROM Article a WHERE a.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public int update(BidArticleDto bid) {
        return this.entityManager.getEntityManager().createQuery(
                "UPDATE Article e SET e.currentPrice = :amount,e.lastBidder = :bidder " +
                        "WHERE e.id = :id")
                .setParameter("amount", bid.getAmount())
                .setParameter("bidder", bid.getBidder())
                .setParameter("id", bid.getId())
                .executeUpdate();
    }
}
