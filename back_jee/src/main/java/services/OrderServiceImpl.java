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

    @Inject
    private RMQCommunicationService communicationService;

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
                return this.updateOrderStatusSet(order, ORDERSTEPTWO);
            case ORDERSTEPTWO:
                int status = this.communicationService.sendOrder(order.getId() + "");
                if (status == 0) {
                    return this.updateOrderStatusSet(order, ORDERSTEPTHREE);
                }
                break;
            case ORDERSTEPTHREE:
                return this.updateOrderStatusSet(order, ORDERSTEPFOUR);
        }
        return order;
    }

    private Order updateOrderStatusSet(Order order, String newStatus) {
        int updated = this.orderDao.update(order.getId(), newStatus);
        if (updated == 0) {
            return null;
        }
        return new Order(order.getId(), order.getBuyer(), order.getArticleId(), newStatus, order.getFirstname(),
                order.getLastname(), order.getStreet(), order.getZipcode(), order.getCity());
    }

}
