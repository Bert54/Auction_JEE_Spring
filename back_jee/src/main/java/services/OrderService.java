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

    public List<Order> getOrdersBySeller(String seller);

    public Order saveOrder(CreateOrderDto newOrder);

    public Order updateOrder(Order order);

    public void updateOrderFromShippingApplication(long id);

    public void confirmOrderReceptionFromShippingApplication(long id);

}
