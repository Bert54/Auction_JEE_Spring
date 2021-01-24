package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @Query("SELECT o FROM Order AS o WHERE o.buyer = :username")
    public Iterable<Order> findAll(String username);

    @Query("SELECT o FROM Order AS o WHERE o.id = :id")
    public Order find(long id);

    @Query("SELECT o FROM Order AS o WHERE o.articleId = :articleId")
    public Order findByArticleId(long articleId);

    @Query("SELECT o FROM Order o JOIN Article a ON o.articleId = a.id WHERE a.seller = :seller")
    public Iterable<Order> findBySeller(String seller);

    public Order save(Order order);

    @Transactional
    @Modifying
    @Query("UPDATE Order o SET o.status = :newStatus WHERE o.id = :id")
    public int update(long id, String newStatus);

}
