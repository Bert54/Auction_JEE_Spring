package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article")
public class Article implements Serializable {

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
    private String categories;

    @Column(name="ENDINGDATE")
    private long endingDate;

    @Column(name="SELLER")
    private String seller;

    @Column(name="LASTBIDDER")
    private String lastBidder;

    public Article() {}

    public Article(String name, String description, double startingPrice,
                   double currentPrice, String categories, long endingDate,
                   String seller, String lastBidder) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
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

    public String getSeller() { return seller; }

    public String getLastBidder() {
        return lastBidder;
    }
}
