package com.ul.gla.auctionbackspring.entities;

import javax.persistence.*;

@Entity
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String description;
    private double startingPrice;
    private double currentPrice;
    private String categories;  // Separator: ","
    private long endingDate;

    public Article() {}

    /**
     * There is no "actualPrice"  because it's defined by "startingPrice"
     * @param name
     * @param description
     * @param startingPrice define the price for the article (and the actual price)
     * @param categories
     * @param endingDate
     */
    public Article(String name, String description, double startingPrice, String categories, long endingDate) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.categories = categories;
        this.endingDate = endingDate;
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
}
