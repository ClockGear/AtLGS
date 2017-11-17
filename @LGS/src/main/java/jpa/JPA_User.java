package jpa;

import domain.User;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class JPA_User extends Facade<User>{
    @PersistenceContext
    private EntityManager entityManager;

    public JPA_User() {
        super(User.class);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(User entity) {
        super.create(entity);
    }

    @Override
    public void edit(User entity) {
        super.edit(entity);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public User find(long id) {
        return super.find(id);
    }

    @Override
    public List<User> getAll() {
        return super.getAll();
    }
}
