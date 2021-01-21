package entities;

import javax.persistence.*;

@Entity
@Table(name = "articleorder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String buyer;

    @Column(unique=true)
    private long articleId;

    private String status;

    private String firstname;

    private String lastname;

    private String street;

    private int zipcode;

    private String city;

    public Order() {}

    public Order(String buyer, long articleId, String status, String firstname, String lastname,
                 String street, int zipcode, String city) {
        this.buyer = buyer;
        this.articleId = articleId;
        this.status = status;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public String getBuyer() {
        return buyer;
    }

    public long getArticleId() {
        return articleId;
    }

    public String getStatus() {
        return status;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getStreet() {
        return street;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }
}
