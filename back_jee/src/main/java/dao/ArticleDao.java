package dao;

import dto.BidArticleDto;
import entities.Article;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ArticleDao {

    public List<Article> findAll(long timestamp);

    public List<Article> findAll(String username);

    public Article find(long id);

    public List<Article> find(String name, long timestamp);

    public List<Article> find(long timestamp, String category);

    public List<Article> find(String username);

    public Article save(Article article);

    public int delete(long id);

    public int update(BidArticleDto bid);

}
