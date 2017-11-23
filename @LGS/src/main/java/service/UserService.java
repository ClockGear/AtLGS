package service;

import domain.User;
import jpa.JPA_User;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
@Dependent
public class UserService implements IService<User>{
    @Inject
    private JPA_User jpaUser;

    public void create(User entity) {
        jpaUser.create(entity);
    }

    public void edit(User entity) {
        jpaUser.edit(entity);
    }

    public void remove(long id) {
        jpaUser.remove(id);
    }

    public User find(long id) {
        return jpaUser.find(id);
    }

    public User find(String email) {
        return jpaUser.find(email);
    }

    public List<User> getAll() {
        return jpaUser.getAll();
    }
}
