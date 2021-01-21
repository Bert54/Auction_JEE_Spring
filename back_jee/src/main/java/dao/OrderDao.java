package dao;

import entities.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderDao {

    public List<Order> fetchAll(String username);

    public Order fetchByArticleId(long articleId);

    public Order save(Order order);

}
