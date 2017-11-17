package service;

import domain.Presence;
import jpa.JPA_Presence;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class PresenceService implements IService<Presence> {
    @Inject
    private JPA_Presence jpaPresence;


    public void create(Presence entity) {
        jpaPresence.create(entity);
    }

    public void edit(Presence entity) {
        jpaPresence.edit(entity);
    }

    public void remove(long id) {
        jpaPresence.remove(id);
    }

    public Presence find(long id) {
        return jpaPresence.find(id);
    }

    public List<Presence> getAll() {
        return jpaPresence.getAll();
    }
}
