package com.ul.gla.auctionbackspring.controllers;

import com.ul.gla.auctionbackspring.dto.CreateOrderDto;
import com.ul.gla.auctionbackspring.entities.Article;
import com.ul.gla.auctionbackspring.entities.Order;
import com.ul.gla.auctionbackspring.services.ArticleService;
import com.ul.gla.auctionbackspring.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "auctions/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public Iterable<Order> getOrders() {
        return this.orderService.getOrdersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping(value = "/{id}")
    public Order getOrder(@PathVariable("id") long id) {
        Order order = this.orderService.getOrderById(id);
        if (order == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Order with id " + id + " was not found",
                    new Exception());
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!order.getBuyer().equals(username) &&
                !this.articleService.getArticle(order.getArticleId()).getSeller().equals(username)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Order with id " + id + " was not created by user " +
                            username + " and is not the seller of its article",
                    new Exception());
        }
        return order;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderDto newOrder) {
        if (newOrder == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No data provided",
                    new Exception());
        }
        if (newOrder.getArticleId() == 0 || newOrder.getFirstname() == null || newOrder.getLastname() == null ||
                newOrder.getStreetName() == null || newOrder.getZipcode() < 10000 || newOrder.getZipcode() > 99999 ||
                newOrder.getCity() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Malformed body",
                    new Exception());
        }
        Article article = this.articleService.getArticle(newOrder.getArticleId());
        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article with id \" + newOrder.getArticleId() + \" was not found",
                    new Exception());
        }
        if (article.getEndingDate() > System.currentTimeMillis() / 1000) {
           throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,  "Article with id " + newOrder.getArticleId() + " has not reached its deadline",
                    new Exception());
        }
        if (!article.getLastBidder().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                   " Article with id " + newOrder.getArticleId() + " has not been won by " +
                        SecurityContextHolder.getContext().getAuthentication().getName(),
                    new Exception());
        }
        Order order = this.orderService.getOrderByArticleId(newOrder.getArticleId());
        if (order != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "An order for article with id " +
                            newOrder.getArticleId() + " has already been created",
                    new Exception());
        }
        newOrder.setBuyer(SecurityContextHolder.getContext().getAuthentication().getName());
        order =  this.orderService.saveOrder(newOrder);
        return order;
    }

    @GetMapping(value = "/received")
    public Iterable<Order> getOrdersToProcess() {
        return this.orderService.getOrdersBySeller(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PutMapping(value = "/received/update/{id}")
    public Order updateOrderStatus(@PathVariable("id") long id) {
        Order order = this.orderService.getOrderById(id);
        if (order == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "order with id " + id + " was not found",
                    new Exception());
        }
        if (!articleService.getArticle(order.getArticleId()).getSeller().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Order with id " + order.getId() + " cannot be updated by " +
                            SecurityContextHolder.getContext().getAuthentication().getName(),
                    new Exception());
        }
        order = this.orderService.updateOrder(order);
        if (order == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Could not update order with id " + id,
                    new Exception());
        }
        return order;
    }

}
