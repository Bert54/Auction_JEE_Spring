package services;

import dao.ArticleDao;
import dto.AddArticleDto;
import entities.Article;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;

@Singleton
public class ArticleServiceImpl implements ArticleService {

    @Inject
    private ArticleDao articledao;

    @Override
    public Article addArticle(AddArticleDto article) {
        return this.articledao.save(new Article(article.getName(), article.getDescription(),
                article.getStartingPrice(), article.getCurrentPrice(), article.getCategories(),
                article.getEndingDate(), article.getSeller()));
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
        return this.articledao.delete(id);
    }

}
