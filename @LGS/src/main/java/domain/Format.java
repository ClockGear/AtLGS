package domain;

import enums.Game;

import javax.persistence.*;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "getAllFormats", query = "select f from Format f")
)
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private Game game;

    public Format(long id, String name, String description, Game game) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.game = game;
    }

    public Format(String name, String description, Game game) {
        this.name = name;
        this.description = description;
        this.game = game;
    }

    public Format() {
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

    public Game getGame() {
        return game;
    }
}
