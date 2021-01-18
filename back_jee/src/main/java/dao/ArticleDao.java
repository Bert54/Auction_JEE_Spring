package dao;

import entities.Article;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ArticleDao {

    public List<Article> findAll(String username);

    public Article find(long id);

    public Article save(Article article);

    public int delete(long id);

}
