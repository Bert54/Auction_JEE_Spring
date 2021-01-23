package services;

import dao.OrderDao;
import dto.CreateOrderDto;
import entities.Order;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class OrderServiceImpl implements OrderService {

    private static final String ORDERSTEPONE = "Order sent";
    private static final String ORDERSTEPTWO = "Order confirmed";
    private static final String ORDERSTEPTHREE = "Order awaiting shipment";
    private static final String ORDERSTEPFOUR = "Order shipped";

    @Inject
    private OrderDao orderDao;

    @Override
    public List<Order> getOrdersByUsername(String username) {
        return this.orderDao.findAll(username);
    }

    @Override
    public Order getOrderById(long id) {
        return this.orderDao.find(id);
    }

    @Override
    public Order getOrderByArticleId(long articleId) {
        return this.orderDao.findByArticleId(articleId);
    }

    @Override
    public List<Order> getOrdersBySeller(String seller) {
        return this.orderDao.findBySeller(seller);
    }

    @Override
    public Order saveOrder(CreateOrderDto newOrder) {
        Order order = new Order(newOrder.getBuyer(), newOrder.getArticleId(), ORDERSTEPONE, newOrder.getFirstname(),
                newOrder.getLastname(), newOrder.getStreetNumber() + " " + newOrder.getStreetName(),
                newOrder.getZipcode(), newOrder.getCity());
        return this.orderDao.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        switch (order.getStatus()) {
            case ORDERSTEPONE:
                return this.updateOrderStatusSet(order.getId(), ORDERSTEPTWO);
            case ORDERSTEPTWO:
                return this.updateOrderStatusSet(order.getId(), ORDERSTEPTHREE);
            case ORDERSTEPTHREE:
                return this.updateOrderStatusSet(order.getId(), ORDERSTEPFOUR);
        }
        return order;
    }

    private Order updateOrderStatusSet(long orderId, String newStatus) {
        int updated = this.orderDao.update(orderId, newStatus);
        if (updated == 0) {
            return null;
        }
        return this.orderDao.find(orderId);
    }

}
