package entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String street;

    private String city;

    private Integer postcode;

    private Integer houseNumber;

    public User() {}


    public User(String username, String password, String firstName, String lastName, String street, String city, Integer postcode, Integer houseNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this. postcode = postcode;
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

    public Integer getPostcode() {
        return postcode;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }


}
