package dao;

import configuration.EntityManagerProvider;
import entities.Article;
import entities.Order;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
public class OrderDaoImpl implements OrderDao {

    @Inject
    private EntityManagerProvider entityManager;

    @Override
    public List<Order> findAll(String username) {
        TypedQuery<Order> query = this.entityManager.getEntityManager().createQuery(
                "SELECT o FROM Order AS o WHERE o.buyer = :username", Order.class)
                .setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public Order find(long id) {
        TypedQuery<Order> query = this.entityManager.getEntityManager().createQuery(
                "SELECT o FROM Order AS o WHERE o.id = :id", Order.class)
                .setParameter("id", id);
        List<Order> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Order findByArticleId(long articleId) {
        TypedQuery<Order> query = this.entityManager.getEntityManager().createQuery(
                "SELECT o FROM Order AS o WHERE o.articleId = :articleId", Order.class)
                .setParameter("articleId", articleId);
        List<Order> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<Order> findBySeller(String seller) {
        TypedQuery<Order> query = this.entityManager.getEntityManager().createQuery(
                "SELECT o FROM Order o JOIN Article a ON o.articleId = a.id WHERE a.seller = :seller", Order.class)
                .setParameter("seller", seller);
        return query.getResultList();
    }

    @Override
    public Order save(Order order) {
        this.entityManager.getEntityManager().persist(order);
        return order;
    }

    @Override
    public int update(long id, String newStatus) {
        return this.entityManager.getEntityManager().createQuery(
                "UPDATE Order o SET o.status = :newStatus WHERE o.id = :id")
                .setParameter("id", id)
                .setParameter("newStatus", newStatus)
                .executeUpdate();
    }

}
