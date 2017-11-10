package domain;

import javax.persistence.*;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "getAllUsers", query = "select u from User u")
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String username;
    private String password;
    private String email;

    public User(long id, String name, String username, String password, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String name, String username, String password, String email) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
