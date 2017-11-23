package jpa;

import domain.Presence;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class JPA_Presence extends Facade<Presence> {
    @PersistenceContext
    private EntityManager entityManager;

    public JPA_Presence() {
        super(Presence.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(Presence entity) {
        super.create(entity);
    }

    @Override
    public void edit(Presence entity) {
        super.edit(entity);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public Presence find(long id) {
        return super.find(id);
    }

    public List<Presence> getAll() {
        return this.getEntityManager().createNamedQuery("getAllPresences",Presence.class).getResultList();
    }
}
