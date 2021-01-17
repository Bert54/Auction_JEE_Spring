package dao;

import configuration.EntityManagerProvider;
import entities.Article;
import entities.User;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;

@Singleton
public class ArticleDaoImpl implements ArticleDao {

    @Inject
    private EntityManagerProvider entityManager;

    @Override
    public List<Article> findAll(String username) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE a.seller = :username", Article.class)
                .setParameter("username", username);
        return query.getResultList();
    }

    public Article find(long id) {
        TypedQuery<Article> query = this.entityManager.getEntityManager().createQuery(
                "SELECT a FROM Article AS a WHERE a.id = :id", Article.class)
                .setParameter("id", id);
        List<Article> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Article save(Article article) {
        this.entityManager.getEntityManager().persist(article);
        return article;
    }
}
