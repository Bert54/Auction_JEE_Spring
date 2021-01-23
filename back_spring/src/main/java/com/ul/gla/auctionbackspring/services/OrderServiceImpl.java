package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dao.OrderRepository;
import com.ul.gla.auctionbackspring.dto.CreateOrderDto;
import com.ul.gla.auctionbackspring.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private static final String ORDERSTEPONE = "Order sent";
    private static final String ORDERSTEPTWO = "Order confirmed";
    private static final String ORDERSTEPTHREE = "Order awaiting shipment";
    private static final String ORDERSTEPFOUR = "Order shipped";

    @Autowired
    private OrderRepository orderDao;

    @Override
    public Iterable<Order> getOrdersByUsername(String username) {
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
    public Iterable<Order> getOrdersBySeller(String seller) {
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
                return this.updateOrderStatusSet(order, ORDERSTEPTHREE);
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
