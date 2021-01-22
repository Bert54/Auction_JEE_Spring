package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dto.AddArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;

public interface ArticleService {

    public Iterable<Article> findAll();

    public Article addArticle(AddArticleDto article);

    public Article getArticle(long id);

    public Iterable<Article> getArticles(String username);

    public int deleteArticle(long id);

    public Iterable<Article> filterArticles(String name, String categories);

}
