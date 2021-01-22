package entities;

import javax.persistence.*;

@Entity
@Table(name = "bid")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="BIDDER")
    private String bidder;

    @Column(name="ARTICLEID")
    private long articleId;

    public Bid() {}

    public Bid(String bidder, long articleId) {
        this.bidder = bidder;
        this.articleId = articleId;
    }

    public long getId() {
        return id;
    }

    public String getBidder() {
        return bidder;
    }

    public long getArticleId() {
        return articleId;
    }

}
