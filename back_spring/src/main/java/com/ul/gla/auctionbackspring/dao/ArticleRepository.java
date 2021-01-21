package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository  extends CrudRepository<Article, Integer> {

    @Query("SELECT article FROM Article article WHERE article.name = :name")
    Article findByName(String name);
    Article find(long id);
    Iterable<Article> find(String name, long timestamp);
    Iterable<Article> find(long timestamp, String category);


    Iterable<Article> findAll(String username);
    Iterable<Article> findAll(long timestamp);

    Iterable<Article> findAllByCategoriesContaining(String category);

    int delete(long id);

    Article save(Article article);
}
