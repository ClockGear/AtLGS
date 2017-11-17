package jpa;

import domain.EventPresence;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class JPA_EventPresence extends Facade<EventPresence> {
    @PersistenceContext(unitName = "AtLGSUnit")
    private EntityManager entityManager;

    public JPA_EventPresence() {
        super(EventPresence.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(EventPresence entity) {
        super.create(entity);
    }

    @Override
    public void edit(EventPresence entity) {
        super.edit(entity);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public EventPresence find(long id) {
        return super.find(id);
    }

    @Override
    public List<EventPresence> getAll() {
        return super.getAll();
    }
}
