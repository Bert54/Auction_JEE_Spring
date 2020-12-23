package dao;

import entities.User;

import javax.ejb.Singleton;
import javax.persistence.*;
import java.util.List;

@Singleton
public class UserDaoImpl implements UserDao {

    @PersistenceUnit
    private final EntityManagerFactory emfactory;
    @PersistenceContext
    EntityManager entitymanager;

    public UserDaoImpl() {
        emfactory = Persistence.createEntityManagerFactory("Auctions");
        entitymanager = emfactory.createEntityManager();
    }

    @Override
    public List<User> findByUserName(String username) {
        TypedQuery<User> query = entitymanager.createQuery(
                "SELECT u FROM User AS u WHERE u.username = :username", User.class)
                .setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public User save(User user) {
        this.entitymanager.persist(user);
        return user;
    }
}
