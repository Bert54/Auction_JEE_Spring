package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dto.CreateOrderDto;
import com.ul.gla.auctionbackspring.entities.Order;

import java.util.List;

public interface OrderService {

    public Iterable<Order> getOrdersByUsername(String username);

    public Order getOrderById(long id);

    public Order getOrderByArticleId(long articleId);

    public Iterable<Order> getOrdersBySeller(String seller);

    public Order saveOrder(CreateOrderDto newOrder);

    public Order updateOrder(Order order);

    public void updateOrderFromShippingApplication(long id);

    public void confirmOrderReceptionFromShippingApplication(long id);
}
