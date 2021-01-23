package com.ul.gla.auctionbackspring.controllers;

import com.ul.gla.auctionbackspring.dto.AddArticleDto;
import com.ul.gla.auctionbackspring.dto.BidArticleDto;
import com.ul.gla.auctionbackspring.entities.Article;
import com.ul.gla.auctionbackspring.entities.Bid;
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
import java.util.List;

@RestController
@RequestMapping(value = "auctions/articles")
public class ArticleController {

@Autowired
private ArticleService articleService;

    @GetMapping
    public Iterable<Article> getAllArticle(){
        return articleService.findAll(SecurityContextHolder.getContext().getAuthentication().getName());
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
                    HttpStatus.BAD_REQUEST, "NO data provided",
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

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Article deleteOneArticle(@PathVariable("id") long id) {
        Article article = this.articleService.getArticle(id);
        if (article == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article with id " + id + " was not found",
                    new Exception());
        }
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(article.getSeller())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Article with id " + id + " does not belong to user " +
                        SecurityContextHolder.getContext().getAuthentication().getName(),
                    new Exception());
        }
        long timestamp = System.currentTimeMillis() / 1000;
        if (article.getEndingDate() <= timestamp) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The article's end date with id " + id + " has been reached." +
                            " Articles with reached end dates cannot be deleted",
                    new Exception());
        }
        int result = this.articleService.deleteArticle(id);
        if (result == 0) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not delete article with id " + id,
                    new Exception());
        }
        return article;
    }

    @GetMapping (value = "/search")
    public Iterable<Article> getArticleByFilter(@RequestParam("name") String name, @RequestParam("categories") String categories) {
        return this.articleService.filterArticles(name, categories);
    }

    @PutMapping(value = "/bid")
    public Article bid(BidArticleDto bid){
        if(bid == null || bid.getId() == 0 || bid.getAmount() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Malformed body",new Exception());
        }
        Article article = this.articleService.getArticle(bid.getId());

        if(article == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Article with id " + bid.getId() + " was not found",new Exception());
        }
        if (article.getEndingDate() <= System.currentTimeMillis() / 1000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The article's end date with id "
                    + bid.getId()
                    + " has been reached."
                    +" Articles with reached end dates cannot be updated",
                    new Exception());
        }
        if (article.getCurrentPrice() >= bid.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The article's current bid with id "
                    + bid.getId()
                    + " is equal or higher"
                    +" than proposed amount",
                    new Exception());
        }
        bid.setBidder(SecurityContextHolder.getContext().getAuthentication().getName());
        if (article.getSeller().equals(bid.getBidder())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The bidder "
                    + bid.getBidder()
                    + " of article with id "
                    + bid.getId()
                    + " is also its seller."
                    +" Sellers cannot bid on their own article",
                    new Exception());
        }
        int status = this.articleService.updateArticle(bid);
        if (status == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not update article with id "
                    + bid.getId(),
                    new Exception());
        }
        return this.articleService.getArticle(bid.getId());

    }

    @GetMapping(name = "/userbids")
    public Iterable<Article> getUserBids(){
        return this.articleService.getArticlesByUserBids(SecurityContextHolder.getContext().getAuthentication().getName());
    }



}
