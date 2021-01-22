package com.ul.gla.auctionbackspring.controllers;

import com.ul.gla.auctionbackspring.dto.AddArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;
import com.ul.gla.auctionbackspring.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auctions/articles")
public class ArticleController {

@Autowired
private ArticleService articleService;

    @GetMapping (value = "/all")
    public Iterable<Article> getAll(){
        return articleService.findAll();
    }

    @PostMapping (value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addArticle(@RequestBody AddArticleDto newArticle){
        Article article = this.articleService.addArticle(newArticle);
//        if (article == null) {
//            throw new ResponseStatusException(
//                    HttpStatus.CONFLICT, "Error when trying to add a new article : article = null",
//                    new Exception("Article"));
//        }

    }
}
