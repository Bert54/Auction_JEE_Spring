package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dto.ArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;

import java.util.List;

public interface ArticleService {

    public Article addArticle(ArticleDto article);

    public Article getArticle(long id);

    public Iterable<Article> getArticles(String username);

    public int deleteArticle(long id);

    public Iterable<Article> filterArticles(String name, String categories);

}
