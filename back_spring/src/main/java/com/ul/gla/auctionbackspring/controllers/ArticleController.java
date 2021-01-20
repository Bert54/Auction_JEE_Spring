package com.ul.gla.auctionbackspring.controllers;

import com.ul.gla.auctionbackspring.dao.ArticleRepository;
import com.ul.gla.auctionbackspring.entities.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auctions/articles")
public class ArticleController {

@Autowired
private ArticleRepository dao;


    @GetMapping (value = "/all")
    public Iterable<Article> getAll(){
        return dao.findAll();
    }

}
