package domain;

import enums.Certainty;

import javax.persistence.*;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "getAllEventPresences", query = "select ep from EventPresence ep")
)
public class EventPresence {
    @Id
    @GeneratedValue
    private long id;
    private Certainty certainty;

    @ManyToOne
    private User user;

    @ManyToOne
    private GameEvent event;

    public EventPresence(long id, Certainty certainty, User user, GameEvent event) {
        this.id = id;
        this.certainty = certainty;
        this.user = user;
        this.event = event;
    }

    public EventPresence(Certainty certainty, User user, GameEvent event) {
        this.certainty = certainty;
        this.user = user;
        this.event = event;
    }

    public EventPresence() {

    }

    public long getId() {
        return id;
    }

    public Certainty getCertainty() {
        return certainty;
    }

    public User getUser() {
        return user;
    }

    public GameEvent getEvent() {
        return event;
    }
}
