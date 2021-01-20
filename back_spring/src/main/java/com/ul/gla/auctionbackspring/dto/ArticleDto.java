package com.ul.gla.auctionbackspring.dto;

public class ArticleDto {

    private String name;
    private String description;
    private double startingPrice;
    private double currentPrice;
    private String categories;  // Separator: ","
    private long endingDate;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(double startingPrice) {
        this.startingPrice = startingPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public long getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(long endingDate) {
        this.endingDate = endingDate;
    }
}
