package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dto.AddArticleDto;
import com.ul.gla.auctionbackspring.dto.BidArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;

public interface ArticleService {

    public Iterable<Article> findAll(String name);

    public Article addArticle(AddArticleDto article);

    public Article getArticle(long id);

    public Iterable<Article> getArticles(String username);

    public int deleteArticle(long id);

    public Iterable<Article> filterArticles(String name, String categories);

    public int updateArticle(BidArticleDto bid);

    public Iterable<Article> getArticlesByUserBids(String username);
}
