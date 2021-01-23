package com.ul.gla.auctionbackspring.controllers;

import com.ul.gla.auctionbackspring.dto.AddArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;
import com.ul.gla.auctionbackspring.exceptions.ArticleNotFoundException;
import com.ul.gla.auctionbackspring.exceptions.ArticleWithMalformedBodyException;
import com.ul.gla.auctionbackspring.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "auctions/articles")
public class ArticleController {

@Autowired
private ArticleService articleService;

    @GetMapping
    public Iterable<Article> getAll(){
        return articleService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Article getOneArticle(@PathVariable("id") Long id) {
        Article article = articleService.getArticle(id);
        if(article == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Article with the corresponding id: "+id+"  found",
                    new ArticleNotFoundException("Article not found")
            );
        }
        return article;
    }


    @PostMapping (value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Article addArticle(@RequestBody AddArticleDto newArticle){
        if (newArticle == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No data provided",
                    new Exception());
        }
        if(newArticle.getName() == null || newArticle.getCategories() == null || newArticle.getEndingDate() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Malformed body, please put one name, at least one category and an ending date",
                    new ArticleWithMalformedBodyException());
        }
        newArticle.setSeller(SecurityContextHolder.getContext().getAuthentication().getName());
        newArticle.setCurrentPrice(newArticle.getStartingPrice());
        Article article = this.articleService.addArticle(newArticle);
        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't create article",
                    new Exception());
        }
        return article;
    }
}
