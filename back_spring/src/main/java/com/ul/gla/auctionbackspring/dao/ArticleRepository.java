package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository  extends CrudRepository<Article, Integer> {

    @Query("SELECT article FROM Article article WHERE article.name = :name")
    Article findByName(String name);
    Iterable<Article> findAllByCategoriesContaining(String category);

    Article save(Article article);
}
