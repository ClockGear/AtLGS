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

    public User find(String email) {
        User foundUser = null;
        for (User user : getAll()) {
            if (user.getEmail().equals(email)) {
                foundUser = user;
            }
        }
        return foundUser;
    }

    public List<User> getAll() {
        return this.getEntityManager().createNamedQuery("getAllUsers",User.class).getResultList();
    }
}
