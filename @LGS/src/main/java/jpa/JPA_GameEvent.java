package jpa;

import domain.GameEvent;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class JPA_GameEvent extends Facade<GameEvent> {
    @PersistenceContext(unitName = "AtLGSUnit")
    private EntityManager entityManager;

    public JPA_GameEvent() {
        super(GameEvent.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(GameEvent entity) {
        super.create(entity);
    }

    @Override
    public void edit(GameEvent entity) {
        super.edit(entity);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public GameEvent find(long id) {
        return super.find(id);
    }

    @Override
    public List<GameEvent> getAll() {
        return super.getAll();
    }
}
