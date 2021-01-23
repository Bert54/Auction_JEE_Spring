package com.ul.gla.auctionbackspring.dao;

import com.ul.gla.auctionbackspring.entities.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ArticleRepository extends CrudRepository<Article, Integer> {

    @Query("SELECT article FROM Article article WHERE article.name = :name")
    Article findByName(String name);

    @Query("SELECT a FROM Article AS a WHERE a.id = :id")
    Article find(long id);

    @Query("SELECT a FROM Article AS a WHERE LOWER(a.name) LIKE LOWER(:name) AND a.endingDate > :timestamp")
    Iterable<Article> find(String name, long timestamp);

    @Query("SELECT a FROM Article AS a WHERE LOWER(a.categories) LIKE LOWER(:category) AND a.endingDate > :timestamp")
    Iterable<Article> find(long timestamp, String category);

    @Query("SELECT a FROM Article AS a WHERE a.id = :id")
    Article find(Long id);

    @Query("SELECT article FROM Article article WHERE article.name = :name")
    Iterable<Article> findAll(String name);

    @Query("SELECT a FROM Article AS a WHERE a.endingDate = :timestamp")
    Iterable<Article> findAll(long timestamp);

    @Transactional
    @Modifying
    @Query("DELETE FROM Article AS a WHERE a.id = :id")
    int delete(long id);

    Article save(Article article);
}
