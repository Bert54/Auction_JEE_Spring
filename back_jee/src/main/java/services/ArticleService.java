package services;

import dto.AddArticleDto;
import entities.Article;

import javax.ejb.Local;

@Local
public interface ArticleService {

    public Article addArticle(AddArticleDto article);

}
