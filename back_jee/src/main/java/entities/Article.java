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

    private double actualPrice;

    private String categories;

    private long endingDate;

    public Article() {}

    public Article(String name, String description, double startingPrice,
                   double actualPrice, String categories, long endingDate) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        this.actualPrice = actualPrice;
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

    public double getActualPrice() {
        return actualPrice;
    }

    public String getCategories() {
        return categories;
    }

    public long getEndingDate() {
        return endingDate;
    }

}
