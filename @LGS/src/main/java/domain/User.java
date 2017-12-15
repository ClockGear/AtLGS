package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.Role;

import javax.persistence.*;
import java.util.Date;

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
    private String firstName;
    private String lastName;
    private String password;
    @Column(unique = true)
    private String email;
    private Role role;
    private String token;
    private Date tokenExpiration;
    private byte[] salt;

    @ManyToOne
    private LGS ownedLGS;

    public User(long id, String name, String username, String password, String email, Role role, byte[] salt) {
        this.id = id;
        this.firstName = name;
        this.lastName = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.salt = salt;
    }

    public User(long id, String name, String username, String password, String email, Role role, byte[] salt, LGS ownedLGS) {
        this.id = id;
        this.firstName = name;
        this.lastName = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.salt = salt;
        this.ownedLGS = ownedLGS;
    }

    public User(String name, String username, String password, String email, Role role, byte[] salt) {
        this.firstName = name;
        this.lastName = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.salt = salt;
    }

    public User(String name, String username, String password, String email, Role role, byte[] salt, LGS ownedLGS) {
        this.firstName = name;
        this.lastName = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.salt = salt;
        this.ownedLGS = ownedLGS;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonIgnore
    public String getToken() {
        return token;
    }

    @JsonIgnore
    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void refreshToken(String token, Date tokenExpiration) {
        this.token = token;
        this.tokenExpiration = tokenExpiration;
    }

    @JsonIgnore
    public byte[] getSalt() {
        return salt;
    }

    public LGS getOwnedLGS() {
        return ownedLGS;
    }

    public void setOwnedLGS(LGS ownedLGS) {
        this.ownedLGS = ownedLGS;
    }
}
