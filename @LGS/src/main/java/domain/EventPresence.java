package domain;

import javax.persistence.*;

/**
 * Created by Dennis van Opstal on 10-11-2017.
 */
@Entity
@NamedQueries(
        @NamedQuery(name = "getAll", query = "select ep from EventPresence ep")
)
public class EventPresence {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private GameEvent event;

    public EventPresence(long id, User user, GameEvent event) {
        this.id = id;
        this.user = user;
        this.event = event;
    }

    public EventPresence(User user, GameEvent event) {
        this.user = user;
        this.event = event;
    }

    public EventPresence() {

    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public GameEvent getEvent() {
        return event;
    }
}
