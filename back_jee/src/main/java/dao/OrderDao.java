package dao;

import entities.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderDao {

    public List<Order> findAll(String username);

    public Order find(long id);

    public Order findByArticleId(long articleId);

    public List<Order> findBySeller(String seller);

    public Order save(Order order);

    public int update(long id, String newStatus);

}
