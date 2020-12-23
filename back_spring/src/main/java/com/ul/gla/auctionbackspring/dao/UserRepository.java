package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT user FROM User user WHERE user.username = :username")
    User findByUsername(String username);

    User save(User user);

}
