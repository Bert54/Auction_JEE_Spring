package com.ul.gla.auctionbackspring.entities;

import javax.persistence.*;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="NAME")
    private String name;
    @Column(name="DESCRIPTION")
    private String description;
    @Column(name="STARTINGPRICE")
    private double startingPrice;
    @Column(name="CURRENTPRICE")
    private double currentPrice;
    @Column(name="CATEGORIES")
    private String categories;  // Separator: ","
    @Column(name="ENDINGDATE")
    private long endingDate;
    @Column(name="SELLER")
    private String seller;
    @Column(name="LASTBIDDER")
    private String lastBidder;

    public Article() {}

    /**
     * Constructor for an article.
     * We dosn't need lastBidder and currentPrice because :
     * currentPrice is the startingPrice at the begin, and lastBidder is unknown at the start of the bid.
     * @param name name of the product
     * @param description description of the product
     * @param startingPrice starting price of the product
     * @param categories all the catégories: séparator : ","
     * @param endingDate timestamp for the endingDate
     * @param seller username of the seller
     */

    public Article(String name, String description, double startingPrice, String categories, long endingDate, String seller, String lastbidder) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.categories = categories;
        this.endingDate = endingDate;
        this.seller = seller;
        this.lastBidder = lastBidder;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public String getCategories() {
        return categories;
    }

    public long getEndingDate() {
        return endingDate;
    }

    public String getSeller() {
        return seller;
    }

    public String getLastBidder() {
        return lastBidder;
    }
}
