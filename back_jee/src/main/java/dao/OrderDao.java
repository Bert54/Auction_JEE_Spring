package dao;

import entities.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderDao {

    public List<Order> findAll(String username);

    public Order find(long id);

    public Order fetchByArticleId(long articleId);

    public Order save(Order order);

}
