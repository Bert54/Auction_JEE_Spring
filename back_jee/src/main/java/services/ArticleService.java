package services;

import dto.AddArticleDto;
import entities.Article;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ArticleService {

    public Article addArticle(AddArticleDto article);

    public Article getArticle(long id);

    public List<Article> getArticles(String username);

}
