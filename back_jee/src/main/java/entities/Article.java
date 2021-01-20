package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article")
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private double startingPrice;

    private double currentPrice;

    private String categories;

    private long endingDate;

    private String seller;

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
