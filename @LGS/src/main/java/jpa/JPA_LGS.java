package jpa;

import domain.LGS;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class JPA_LGS extends Facade<LGS> {
    @PersistenceContext(unitName = "AtLGSUnit")
    private EntityManager entityManager;

    public JPA_LGS() {
        super(LGS.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(LGS entity) {
        super.create(entity);
    }

    @Override
    public void edit(LGS entity) {
        super.edit(entity);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public LGS find(long id) {
        return super.find(id);
    }

    public List<LGS> getAll() {
        return this.getEntityManager().createNamedQuery("getAllLGS",LGS.class).getResultList();
    }
}
