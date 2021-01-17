package dao;

import entities.Article;

import javax.ejb.Local;

@Local
public interface ArticleDao {

    public Article save(Article article);

}
