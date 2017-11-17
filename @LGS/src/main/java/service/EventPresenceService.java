package service;

import domain.EventPresence;
import jpa.JPA_EventPresence;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class EventPresenceService implements IService<EventPresence>{
    @Inject
    private JPA_EventPresence jpaEventPresence;

    public void create(EventPresence entity) {
        jpaEventPresence.create(entity);
    }

    public void edit(EventPresence entity) {
        jpaEventPresence.edit(entity);
    }

    public void remove(long id) {
        jpaEventPresence.remove(id);
    }

    public EventPresence find(long id) {
        return jpaEventPresence.find(id);
    }

    public List<EventPresence> getAll() {
        return jpaEventPresence.getAll();
    }
}
