package services;

import dto.AddArticleDto;
import dto.BidArticleDto;
import entities.Article;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ArticleService {

    public Article addArticle(AddArticleDto article);

    public Article getArticle(long id);

    public List<Article> getArticles(String username);

    public int deleteArticle(long id);

    public List<Article> filterArticles(String name, String categories);

    public int updateArticle(BidArticleDto bid);

    public List<Article> getArticlesByUserBids(String username);

}
