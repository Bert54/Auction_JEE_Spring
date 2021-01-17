package dao;

import entities.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserDao {

    public List<User> find(String username);

    public User save(User user);

}
