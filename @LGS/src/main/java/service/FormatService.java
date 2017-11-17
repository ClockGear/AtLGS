package service;

import domain.Format;
import jpa.JPA_Format;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class FormatService implements IService<Format>{
    @Inject
    private JPA_Format jpaFormat;

    public void create(Format entity) {
        jpaFormat.create(entity);
    }

    public void edit(Format entity) {
        jpaFormat.edit(entity);
    }

    public void remove(long id) {
        jpaFormat.remove(id);
    }

    public Format find(long id) {
        return jpaFormat.find(id);
    }

    public List<Format> getAll() {
        return jpaFormat.getAll();
    }
}
