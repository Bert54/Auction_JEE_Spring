package com.ul.gla.auctionbackspring.services;

import com.ul.gla.auctionbackspring.dao.ArticleRepository;
import com.ul.gla.auctionbackspring.dao.BidRepository;
import com.ul.gla.auctionbackspring.dto.AddArticleDto;
import com.ul.gla.auctionbackspring.dto.BidArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;
import com.ul.gla.auctionbackspring.entities.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articledao;

    @Autowired
    private BidRepository biddao;

    @Override
    public Article addArticle(AddArticleDto article) {
        return this.articledao.save(new Article(article.getName(), article.getDescription(),
                article.getStartingPrice(), article.getCategories(), article.getEndingDate(), article.getSeller(), ""));
    }

    @Override
    public Iterable<Article> findAll(String name) {
        return articledao.findAll(name);
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

    @Override
    public int updateArticle(BidArticleDto bid) {
        int numAffected = this.articledao.update(bid.getAmount(), bid.getBidder(), bid.getId());
        if (numAffected != 0 && this.biddao.find(bid.getBidder(), bid.getId()) == null) {
            this.biddao.save(new Bid(bid.getBidder(), bid.getId()));
        }
        return numAffected;
    }

    @Override
    public Iterable<Article> getArticlesByUserBids(String username) {
        return this.articledao.find(username);
    }
}
