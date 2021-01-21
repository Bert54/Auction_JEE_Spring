package services;

import dao.ArticleDao;
import dao.BidDao;
import dto.AddArticleDto;
import dto.BidArticleDto;
import entities.Article;
import entities.Bid;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ArticleServiceImpl implements ArticleService {

    @Inject
    private ArticleDao articledao;

    @Inject
    private BidDao biddao;

    @Override
    public Article addArticle(AddArticleDto article) {
        return this.articledao.save(new Article(article.getName(), article.getDescription(),
                article.getStartingPrice(), article.getCurrentPrice(), article.getCategories(),
                article.getEndingDate(), article.getSeller(), ""));
    }

    @Override
    public Article getArticle(long id) {
        return this.articledao.find(id);
    }

    @Override
    public List<Article> getArticles(String username) {
        return this.articledao.findAll(username);
    }

    @Override
    public int deleteArticle(long id) {
        int numDeleted = this.articledao.delete(id);
        if (numDeleted != 0) {
            this.biddao.delete(id);
        }
        return numDeleted;
    }

    @Override
    public List<Article> filterArticles(String name, String categories) {
        String[] categoriesArr;
        int iterationStart = 0;
        if (categories != null && !categories.equals("")) {
            categoriesArr = categories.split(",", -1);
        }
        else {
            categoriesArr = new String[0];
        }
        List<Article> articles = null;
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
        int numAffected = this.articledao.update(bid);
        if (numAffected != 0 && this.biddao.find(bid.getBidder(), bid.getId()) == null) {
            this.biddao.save(new Bid(bid.getBidder(), bid.getId()));
        }
        return numAffected;
    }

    @Override
    public List<Article> getArticlesByUserBids(String username) {
        return this.articledao.find(username);
    }

}
