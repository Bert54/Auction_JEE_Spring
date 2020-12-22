package services;

import dto.LoginUserDto;
import dto.RegisterUserDto;
import entities.User;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@Singleton
public class UserServiceImpl implements UserService {

    @Inject
    private HashingService hashService;

    @PersistenceUnit
    private final EntityManagerFactory emfactory;
    @PersistenceContext
    EntityManager entitymanager;

    public UserServiceImpl() {
        emfactory = Persistence.createEntityManagerFactory("Auctions");
        entitymanager = emfactory.createEntityManager();
    }

    @Override
    public User register(RegisterUserDto newUser) {
        String hashedPassword = this.hashService.hashPassword(newUser.getPassword());
        TypedQuery<User> query = entitymanager.createQuery(
                "SELECT u FROM User AS u WHERE u.username = :username", User.class)
                .setParameter("username", newUser.getUsername());
        List<User> results = query.getResultList();
        if (results.isEmpty()) {
            User user = new User(newUser.getUsername(), hashedPassword);
            this.entitymanager.persist(user);
            return user;
        }
        return null;
    }

    @Override
    public Boolean login(LoginUserDto user) {
        TypedQuery<User> query = entitymanager.createQuery(
                "SELECT u FROM User AS u WHERE u.username = :username", User.class)
                .setParameter("username", user.getUsername());
        List<User> results = query.getResultList();
        if (!results.isEmpty()) {
            String storedPass = results.get(0).getPassword();
            return this.hashService.validatePassword(user.getPassword(), storedPass);
        }
        return null;
    }

}
