package service;

import java.util.List;

/**
 * Created by Dennis van Opstal on 17-11-2017.
 */
public interface IService<T> {
    void create(T entity);
    void edit(T entity);
    void remove(long id);
    T find(long id);
    List<T> getAll();
}
