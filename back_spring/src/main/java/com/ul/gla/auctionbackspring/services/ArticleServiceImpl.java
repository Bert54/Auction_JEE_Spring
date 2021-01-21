package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dao.ArticleRepository;
import com.ul.gla.auctionbackspring.dto.ArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articledao;

    @Override
    public Article addArticle(ArticleDto article) {
        return this.articledao.save(new Article(article.getName(), article.getDescription(),
                article.getStartingPrice(), article.getCategories(), article.getEndingDate()));
    }

    @Override
    public Article getArticle(long id) {
        return this.articledao.find(id);
    }

    @Override
    public Iterable<Article> getArticles(String username) {
        return this.articledao.findAll(username);
    }

    @Override
    public int deleteArticle(long id) {
        return this.articledao.delete(id);
    }

    @Override
    public Iterable<Article> filterArticles(String name, String categories) {
        String[] categoriesArr;
        int iterationStart = 0;
        if (categories != null && !categories.equals("")) {
            categoriesArr = categories.split(",", -1);
        }
        else {
            categoriesArr = new String[0];
        }
        Iterable<Article> articles = null;
        if (name != null && !name.equals("")) {
            articles = this.articledao.find(name, System.currentTimeMillis() / 1000);

        }
        if (articles == null && categoriesArr.length == 0) {
            return this.articledao.findAll(System.currentTimeMillis() / 1000);
        }
        if (articles == null) {
            articles = this.articledao.find(System.currentTimeMillis() / 1000, categoriesArr[0]);
            iterationStart = 1;
        }
        for (int i = iterationStart ; i < categoriesArr.length ; i++) {
            List<Article> filteredList = new ArrayList<>();
            for (Article a: articles) {
                if (a.getCategories().contains(categoriesArr[i])) {
                    filteredList.add(a);
                }
            }
            articles = filteredList;
        }
        return articles;
    }
}
