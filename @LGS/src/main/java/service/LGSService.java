package service;

import domain.LGS;
import jpa.JPA_LGS;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class LGSService implements IService<LGS>{
    @Inject
    private JPA_LGS jpaLgs;

    public void create(LGS entity) {
        jpaLgs.create(entity);
    }

    public void edit(LGS entity) {
        jpaLgs.edit(entity);
    }

    public void remove(long id) {
        jpaLgs.remove(id);
    }

    public LGS find(long id) {
        return jpaLgs.find(id);
    }

    public List<LGS> getAll() {
        return jpaLgs.getAll();
    }
}
