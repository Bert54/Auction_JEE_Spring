package entities;

import javax.persistence.*;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String category;

    private int rebate;

    public Offer() {}

    public Offer(String category, int rebate) {
        this.category = category;
        this.rebate = rebate;
    }

    public long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getRebate() {
        return rebate;
    }

}
