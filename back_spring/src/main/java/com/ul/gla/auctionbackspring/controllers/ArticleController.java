package com.ul.gla.auctionbackspring.controllers;

import com.ul.gla.auctionbackspring.dto.AddArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;
import com.ul.gla.auctionbackspring.exceptions.ArticleWithMalformedBodyException;
import com.ul.gla.auctionbackspring.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "auctions/articles")
public class ArticleController {

@Autowired
private ArticleService articleService;

    @GetMapping
    public Iterable<Article> getAll(){
        return articleService.findAll();
    }


    @PostMapping (value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addArticle(@RequestBody AddArticleDto newArticle){
        if(newArticle.getName() == null || newArticle.getCategories() == null || newArticle.getEndingDate() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "Malformed body, please put one name, at least one category and an ending date",
                    new ArticleWithMalformedBodyException());
        }
        Article article = this.articleService.addArticle(newArticle);
    }
}
