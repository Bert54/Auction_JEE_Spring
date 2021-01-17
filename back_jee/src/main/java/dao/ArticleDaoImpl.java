package dao;

import configuration.EntityManagerProvider;
import entities.Article;

import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class ArticleDaoImpl implements ArticleDao {

    @Inject
    private EntityManagerProvider entityManager;

    @Override
    public Article save(Article article) {
        this.entityManager.getEntityManager().persist(article);
        return article;
    }
}
