package services;

import dao.ArticleDao;
import dto.AddArticleDto;
import entities.Article;

import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class ArticleServiceImpl implements ArticleService {

    @Inject
    private ArticleDao articledao;

    @Override
    public Article addArticle(AddArticleDto article) {
        return this.articledao.save(new Article(article.getName(), article.getDescription(),
                article.getStartingPrice(), article.getActualPrice(), article.getCategories(),
                article.getEndingDate()));
    }

}
