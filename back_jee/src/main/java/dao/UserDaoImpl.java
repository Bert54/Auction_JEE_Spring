package dao;

import configuration.EntityManagerProvider;
import entities.User;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@Singleton
public class UserDaoImpl implements UserDao {

    @Inject
    private EntityManagerProvider entityManager;

    @Override
    public List<User> find(String username) {
        TypedQuery<User> query = this.entityManager.getEntityManager().createQuery(
                "SELECT u FROM User AS u WHERE LOWER(u.username) = LOWER(:username)", User.class)
                .setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public User save(User user) {
        this.entityManager.getEntityManager().persist(user);
        return user;
    }
}
