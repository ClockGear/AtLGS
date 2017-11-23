package jpa;

import domain.Format;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class JPA_Format extends Facade<Format> {
    @PersistenceContext(unitName = "AtLGSUnit")
    private EntityManager entityManager;

    public JPA_Format() {
        super(Format.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(Format entity) {
        super.create(entity);
    }

    @Override
    public void edit(Format entity) {
        super.edit(entity);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public Format find(long id) {
        return super.find(id);
    }

    public List<Format> getAll() {
        return this.getEntityManager().createNamedQuery("getAllFormats",Format.class).getResultList();
    }
}
