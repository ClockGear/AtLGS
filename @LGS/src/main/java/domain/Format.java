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
    private Game game;
    private String abbreviation;

    public Format(long id, String name, Game game, String abbreviation) {
        this.id = id;
        this.name = name;
        this.game = game;
        this.abbreviation = abbreviation;
    }

    public Format(String name, Game game, String abbreviation) {
        this.name = name;
        this.game = game;
        this.abbreviation = abbreviation;
    }

    public Format() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
