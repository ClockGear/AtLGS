package domain;

import javax.persistence.*;

/**
 * Created by Dennis van Opstal on 3-11-2017.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "getAllLGS", query = "SELECT l from LGS l")
})
public class LGS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;

    public LGS(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public LGS(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public LGS() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
