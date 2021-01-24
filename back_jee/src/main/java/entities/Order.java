package entities;

import javax.persistence.*;

@Entity
@Table(name = "articleorder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="BUYER")
    private String buyer;

    @Column(name="PRICE")
    private double price;

    @Column(unique=true, name="ARTICLEID")
    private long articleId;

    @Column(name="STATUS")
    private String status;

    @Column(name="FIRSTNAME")
    private String firstname;

    @Column(name="LASTNAME")
    private String lastname;

    @Column(name="STREET")
    private String street;

    @Column(name="ZIPCODE")
    private int zipcode;

    @Column(name="CITY")
    private String city;

    public Order() {}

    public Order(String buyer, double price, long articleId, String status, String firstname, String lastname,
                 String street, int zipcode, String city) {
        this.buyer = buyer;
        this.price = price;
        this.articleId = articleId;
        this.status = status;
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    public Order(long id, String buyer, double price, long articleId, String status, String firstname, String lastname,
                 String street, int zipcode, String city) {
        this.id = id;
        this.buyer = buyer;
        this.price = price;
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

    public double getPrice() {
        return price;
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
