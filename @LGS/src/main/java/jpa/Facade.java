package jpa;

import domain.User;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import java.util.List;

/**
 * Created by Dennis van Opstal on 9-10-2017.
 */
public abstract class Facade<T> {
    private final Class<T> entityClass;

    public Facade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Resource
    private UserTransaction userTransaction;

    public void create(T entity) {
        try {
            userTransaction.begin();
            this.getEntityManager().persist(entity);
            userTransaction.commit();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void edit(T entity) {
        try {
            userTransaction.begin();
            this.getEntityManager().merge(entity);
            userTransaction.commit();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void remove(long id) {
        try {
            userTransaction.begin();
            this.getEntityManager().remove(this.getEntityManager().find(this.entityClass,id));
            userTransaction.commit();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public T find(long id) {
        return this.getEntityManager().find(this.entityClass, id);
    }

    public List<T> getAll() {
        return this.getEntityManager().createNamedQuery("getAll",entityClass).getResultList();
    }
}
