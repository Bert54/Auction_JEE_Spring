package services;

import dao.OrderDao;
import dto.CreateOrderDto;
import entities.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;

    @Override
    public List<Order> getOrdersByUsername(String username) {
        return this.orderDao.fetchAll(username);
    }

    @Override
    public Order getOrderByArticleId(long articleId) {
        return this.orderDao.fetchByArticleId(articleId);
    }

    @Override
    public Order saveOrder(CreateOrderDto newOrder) {
        Order order = new Order(newOrder.getBuyer(), newOrder.getArticleId(), "Order sent", newOrder.getFirstname(),
                newOrder.getLastname(), newOrder.getStreetNumber() + " " + newOrder.getStreetName(),
                newOrder.getZipcode(), newOrder.getCity());
        return this.orderDao.save(order);
    }

}
