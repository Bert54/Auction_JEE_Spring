package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true, name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="FIRSTNAME")
    private String firstName;

    @Column(name="LASTNAME")
    private String lastName;

    @Column(name="STREET")
    private String street;

    @Column(name="CITY")
    private String city;

    @Column(name="POSTCODE")
    private int postcode;

    @Column(name="HOUSENUMBER")
    private int houseNumber;

    public User() {}


    public User(String username, String password, String firstName, String lastName, String street, String city, int postcode, int houseNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.postcode = postcode;
        this.houseNumber = houseNumber;

    }

    public User(long id, String username, String password, String firstName, String lastName, String street, String city, int postcode, int houseNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.postcode = postcode;
        this.houseNumber = houseNumber;

    }

    public long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public int getPostcode() {
        return postcode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

}
