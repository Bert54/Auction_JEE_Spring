package services;

import dto.CreateOrderDto;
import entities.Order;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderService {

    public List<Order> getOrdersByUsername(String username);

    public Order getOrderById(long id);

    public Order getOrderByArticleId(long articleId);

    public Order saveOrder(CreateOrderDto newOrder);

}
